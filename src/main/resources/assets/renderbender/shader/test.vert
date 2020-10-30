#include frex:shaders/api/vertex.glsl
#include frex:shaders/lib/face.glsl

varying vec2 v_noise_uv;

/******************************************************
  renderbender:shader/test.vert
******************************************************/

void frx_startVertex(inout frx_VertexData data) {
	v_noise_uv = frx_faceUv(data.vertex.xyz, data.normal.xyz);
}

void frx_endVertex(inout frx_VertexData data) {
	// NOOP
}
