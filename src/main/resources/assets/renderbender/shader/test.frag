#version 120

varying vec2 v_noise_uv;

void main() {
	float a = tnoise(v_noise_uv * 2.0, u_time);
	float b = tnoise(v_noise_uv * 4.0, u_time * 2.0);
	float c = tnoise(v_noise_uv * 8.0, u_time * 4.0);
	float d = a * b * c;
	vec4 highlight = mix(vec4(1.0, 0.7, 1.0, 1.0), vec4(0.7, 1.0, 1.0, 1.0), d);
	float m = smootherstep(0.0, 1.0, d);
	gl_FragColor = fog(mix(diffuseColor(), highlight, m));
}
