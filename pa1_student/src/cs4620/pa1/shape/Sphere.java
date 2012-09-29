package cs4620.pa1.shape;

import java.util.HashMap;
import java.util.Map;

/*import javax.vecmath.Point3f;
import javax.vecmath.Point3i;
import javax.vecmath.Vector3f;*/

public class Sphere extends TriangleMesh 
{
	@Override
	public void buildMesh(float tolerance)
	{
		//I didn't use Point3f, Point3i, or Vector3f
		//Is this an okay answer, still?
		
		// TODO: (Problem 2) Fill in the code to create a sphere mesh.
		
		int lgth = Math.round(tolerance * 400);
		System.out.println(lgth);
		
		float[] sphereVertices = new float[lgth * lgth * 3],
				sphereNormals  = new float[lgth * lgth * 3];
		int[] sphereTriangles  = new   int[(lgth - 1) * lgth * 6];
		
		for(int i = 0; i < lgth; i++){
			
			double angle1 = (Math.PI / (lgth - 1)) * i;
			float z = (float)Math.cos(angle1),
				  r = (float)Math.sqrt(1 - z * z);
			
			for(int j = 0; j < lgth; j++){
				
				double angle2 = (2 * Math.PI / lgth) * j;
				float x = r * (float)Math.cos(angle2),
					  y = r * (float)Math.sin(angle2);
				
				sphereVertices[i * lgth * 3 + j * 3] = x;
				sphereVertices[i * lgth * 3 + j * 3 + 1] = y;
				sphereVertices[i * lgth * 3 + j * 3 + 2] = z;
				
				sphereNormals[i * lgth * 3 + j * 3] = x;
				sphereNormals[i * lgth * 3 + j * 3 + 1] = y;
				sphereNormals[i * lgth * 3 + j * 3 + 2] = z;
				
				if(i != lgth - 1){
					sphereTriangles[i * lgth * 6 + j * 6] = i * lgth + j;
					sphereTriangles[i * lgth * 6 + j * 6 + 1] = i * lgth + (j + 1) % lgth;
					sphereTriangles[i * lgth * 6 + j * 6 + 2] = ((i + 1) % lgth) * lgth + j;
					sphereTriangles[i * lgth * 6 + j * 6 + 3] = i * lgth + (j + 1) % lgth;
					sphereTriangles[i * lgth * 6 + j * 6 + 4] = ((i + 1) % lgth) * lgth + j;
					sphereTriangles[i * lgth * 6 + j * 6 + 5] = ((i + 1) % lgth) * lgth + (j + 1) % lgth;
				}
			}
		}
		
		setMeshData(sphereVertices, sphereNormals, sphereTriangles);
	}

	@Override
	public Object getYamlObjectRepresentation()
	{
		Map<Object,Object> result = new HashMap<Object, Object>();
		result.put("type", "Sphere");
		return result;
	}
}
