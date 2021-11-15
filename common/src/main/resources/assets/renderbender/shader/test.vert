#include "frex:shaders/api/vertex.glsl"
#include "frex:shaders/lib/face.glsl"

/******************************************************
  renderbender:shader/test.vert
******************************************************/

void frx_materialVertex() {
#ifndef DEPTH_PASS
	frx_var0.xy = frx_faceUv(frx_vertex.xyz, frx_vertexNormal);
#endif
}

