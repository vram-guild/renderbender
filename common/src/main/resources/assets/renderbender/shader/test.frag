#include "frex:shaders/api/fragment.glsl"
#include "frex:shaders/lib/math.glsl"
#include "frex:shaders/api/world.glsl"

/******************************************************
  renderbender:shader/test.frag
******************************************************/

void frx_materialFragment() {
#ifndef DEPTH_PASS
	float t = frx_renderSeconds;
	float a = frx_noise2dt(frx_var0.xy * 2.0, t);
	float b = frx_noise2dt(frx_var0.xy * 4.0, t * 2.0);
	float c = frx_noise2dt(frx_var0.xy * 8.0, t * 4.0);
	float d = a * b * c;
	vec4 highlight = mix(vec4(1.0, 0.7, 1.0, 1.0), vec4(0.7, 1.0, 1.0, 1.0), d);
	float m = frx_smootherstep(0.0, 1.0, d);
	frx_fragColor = mix(frx_sampleColor, highlight, m);
	frx_fragEmissive = max(0, m - 0.5) * 2.0;
#endif
}
