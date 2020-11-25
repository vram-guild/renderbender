#include "frex:shaders/api/vertex.glsl"
#include "frex:shaders/lib/face.glsl"

/******************************************************
  renderbender:shader/test.vert
******************************************************/

void frx_startVertex(inout frx_VertexData data) {
	frx_var0.xy = frx_faceUv(data.vertex.xyz, data.normal.xyz);
}

void frx_endVertex(inout frx_VertexData data) {
	// NOOP
}
