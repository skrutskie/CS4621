package cs4621.ppa1.shape;

import java.util.HashMap;
import java.util.Map;

public class Cylinder extends TriangleMesh {
	@Override
	public void buildMesh(float tolerance) {
		{
			// TODO: (Problem 2) Fill in the code to create a cylinder mesh.

			int lgth = Math.round(4/tolerance);

			float[] cylVertices = new float[lgth * 4 * 3 + 6], cylNormals = new float[lgth * 4 * 3 + 6];
			int[] cylTriangles = new int[lgth * 3 * 4];

			for (int i = 0; i < lgth; i++) {
				double angle = (2 * Math.PI / lgth) * i;
				float x = (float) Math.cos(angle), z = (float) Math.sin(angle);

				cylVertices[i * 3] = x;
				cylVertices[i * 3 + 1] = 1;
				cylVertices[i * 3 + 2] = z;

				cylNormals[i * 3] = 0;
				cylNormals[i * 3 + 1] = 1;
				cylNormals[i * 3 + 2] = 0;

				cylVertices[i * 3 + lgth * 1 * 3] = x;
				cylVertices[i * 3 + 1 + lgth * 1 * 3] = 1;
				cylVertices[i * 3 + 2 + lgth * 1 * 3] = z;

				cylNormals[i * 3 + lgth * 1 * 3] = x;
				cylNormals[i * 3 + 1 + lgth * 1 * 3] = 0;
				cylNormals[i * 3 + 2 + lgth * 1 * 3] = z;

				cylVertices[i * 3 + lgth * 2 * 3] = x;
				cylVertices[i * 3 + 1 + lgth * 2 * 3] = -1;
				cylVertices[i * 3 + 2 + lgth * 2 * 3] = z;

				cylNormals[i * 3 + lgth * 2 * 3] = 0;
				cylNormals[i * 3 + 1 + lgth * 2 * 3] = -1;
				cylNormals[i * 3 + 2 + lgth * 2 * 3] = 0;

				cylVertices[i * 3 + lgth * 3 * 3] = x;
				cylVertices[i * 3 + 1 + lgth * 3 * 3] = -1;
				cylVertices[i * 3 + 2 + lgth * 3 * 3] = z;

				cylNormals[i * 3 + lgth * 3 * 3] = x;
				cylNormals[i * 3 + 1 + lgth * 3 * 3] = 0;
				cylNormals[i * 3 + 2 + lgth * 3 * 3] = z;

				cylTriangles[i * 12] = i;
				cylTriangles[i * 12 + 1] = ((i + 1) % lgth);
				cylTriangles[i * 12 + 2] = lgth * 4;

				cylTriangles[i * 12 + 3] = i + lgth;
				cylTriangles[i * 12 + 4] = ((i + 1) % lgth) + lgth;
				cylTriangles[i * 12 + 5] = i + lgth * 3;

				cylTriangles[i * 12 + 6] = ((i + 1) % lgth) + lgth;
				cylTriangles[i * 12 + 7] = ((i + 1) % lgth) + lgth * 3;
				cylTriangles[i * 12 + 8] = i + lgth * 3;

				cylTriangles[i * 12 + 9] = lgth * 4 + 1;
				cylTriangles[i * 12 + 10] = i + lgth * 2;
				cylTriangles[i * 12 + 11] = ((i + 1) % lgth) + lgth * 2;
			}

			cylVertices[lgth * 12] = 0;
			cylVertices[lgth * 12 + 1] = 1;
			cylVertices[lgth * 12 + 2] = 0;

			cylNormals[lgth * 12] = 0;
			cylNormals[lgth * 12 + 1] = 1;
			cylNormals[lgth * 12 + 2] = 0;

			cylVertices[lgth * 12 + 3] = 0;
			cylVertices[lgth * 12 + 4] = -1;
			cylVertices[lgth * 12 + 5] = 0;

			cylNormals[lgth * 12 + 3] = 0;
			cylNormals[lgth * 12 + 4] = -1;
			cylNormals[lgth * 12 + 5] = 0;

			super.setMeshData(cylVertices, cylNormals, cylTriangles);
		}
	}

	@Override
	public Object getYamlObjectRepresentation() {
		Map<Object, Object> result = new HashMap<Object, Object>();
		result.put("type", "Cylinder");
		return result;
	}
}
