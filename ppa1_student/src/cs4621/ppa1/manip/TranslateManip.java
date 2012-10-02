package cs4621.ppa1.manip;

import java.util.Stack;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import cs4621.ppa1.util.Util;
import cs4621.ppa1.scene.TransformationNode;

public class TranslateManip extends Manip
{
	@Override
	public void dragged(Vector2f mousePosition, Vector2f mouseDelta)
	{
		// TODO: (Problem 1) Implement this manipulator.
		Vector2f mouseAfter = new Vector2f(mousePosition.x + mouseDelta.x,
										   mousePosition.y + mouseDelta.y);
		
		if(axisMode == PICK_CENTER){
			Vector3f origin = new Vector3f(0, 0, 0);
			
			TransformationNode current = transformationNode.getLowestTransformationNodeAncestor();
			while(current != null){
				current.transform(origin, origin);
				current = current.getLowestTransformationNodeAncestor();
			}
			
			Vector3f p1 = camera.NDCToWorldAt(mousePosition, origin),
					 p2 = camera.NDCToWorldAt(   mouseAfter, origin),
					 p = new Vector3f(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z);
			
			transformationNode.translation.x += p.x;
			transformationNode.translation.y += p.y;
			transformationNode.translation.z += p.z;
		} else {
			Vector3f p_a0 = new Vector3f(0, 0, 0),  // Before: Eye
					 p_a1 = (Vector3f)p_a0.clone(), // Before: Mouse
					 p_b0 = (Vector3f)p_a0.clone(), //  After: Eye
					 p_b1 = (Vector3f)p_a0.clone(), //  After: Mouse
					 ax_0 = (Vector3f)p_a0.clone(), // Axis0
					 ax_1 = (Vector3f)p_a0.clone(); // Axis1
			
			if(axisMode == PICK_X) ax_1.x = 1;
			else if(axisMode == PICK_Y) ax_1.y = 1;
			else if(axisMode == PICK_Z) ax_1.z = 1;
					 
			
			camera.getLineThroughNDC(mousePosition, p_a0, p_a1);
			camera.getLineThroughNDC(mouseAfter   , p_b0, p_b1);
			
			TransformationNode current = transformationNode.getLowestTransformationNodeAncestor();
			//Stack<Matrix4f> matrices = new Stack<Matrix4f>();
			
			while(current != null){
				/*Matrix4f scale = new Matrix4f(),
						 rotX  = new Matrix4f(),
						 rotY  = new Matrix4f(),
						 rotZ  = new Matrix4f(),
						 trans = new Matrix4f();
				
				scale.m00 = current.scaling.x;
				scale.m11 = current.scaling.y;
				scale.m22 = current.scaling.z;
				scale.m33 = 1;
				
				matrices.push(scale);
				
				Vector3f rotation = current.rotation;
				
				rotX.m00 = 1;
				rotY.m11 = 1;
				rotZ.m22 = 1;
				
				double aX = toRadians(rotation.x);
				rotX.m11 = (float)Math.cos(aX);
				rotX.m12 = -(float)Math.sin(aX);
				rotX.m22 = (float)Math.cos(aX);
				rotX.m21 = (float)Math.sin(aX);
				
				double aY = toRadians(rotation.y);
				rotY.m00 = (float)Math.cos(aY);
				rotY.m02 = (float)Math.sin(aY);
				rotY.m22 = (float)Math.cos(aY);
				rotY.m20 = -(float)Math.sin(aY);
				
				double aZ = toRadians(rotation.z);
				rotZ.m00 = (float)Math.cos(aZ);
				rotZ.m01 = -(float)Math.sin(aZ);
				rotZ.m11 = (float)Math.cos(aZ);
				rotZ.m10 = (float)Math.sin(aZ);
				
				matrices.push(rotX);
				matrices.push(rotY);
				matrices.push(rotZ);
				
				trans.m03 = current.translation.x;
				trans.m13 = current.translation.y;
				trans.m23 = current.translation.z;
				trans.m33 = 1;
				
				matrices.push(trans);*/
				
				/*current.transform(p_a0, p_a0);
				current.transform(p_a1, p_a1);
				current.transform(p_b0, p_b0);
				current.transform(p_b1, p_b1);*/
				//current.transform(ax_0, ax_0);
				//current.transform(ax_1, ax_1);
				 //should i be multiplying by inverses?****
				current = current.getLowestTransformationNodeAncestor();
			}
			
			/*while(matrices.size() > 0){
				Matrix4f m = matrices.pop();
				m.invert();
				m.transform(p_a0);
				m.transform(p_a1);
				m.transform(p_b0);
				m.transform(p_b1);
			}*/
			
			float t0, t1;
			t0 = Util.lineNearLine(ax_0, ax_1, p_a0, p_a1);
			t1 = Util.lineNearLine(ax_0, ax_1, p_b0, p_b1);
			switch(axisMode){
			case PICK_X:
				//t0 = Util.lineNearLine(new Vector3f(0, 0, 0), new Vector3f(1, 0, 0), p_a0, p_a1);
				//t1 = Util.lineNearLine(new Vector3f(0, 0, 0), new Vector3f(1, 0, 0), p_b0, p_b1);
				transformationNode.translation.x += t1 - t0;
				
				break;
				
			case PICK_Y:
				//t0 = Util.lineNearLine(new Vector3f(0, 0, 0), new Vector3f(0, 1, 0), p_a0, p_a1);
				//t1 = Util.lineNearLine(new Vector3f(0, 0, 0), new Vector3f(0, 1, 0), p_b0, p_b1);
				transformationNode.translation.y += t1 - t0;
				break;
				
			case PICK_Z:
				//t0 = Util.lineNearLine(new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), p_a0, p_a1);
				//t1 = Util.lineNearLine(new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), p_b0, p_b1);
				transformationNode.translation.z += t1 - t0;
				break;
			}
		}
	}
	
	private double toRadians (float a) {
		return ((float)a * Math.PI) / 180;
	}

	private Vector3f xAxis = new Vector3f();
	private Vector3f yAxis = new Vector3f();
	private Vector3f zAxis = new Vector3f();

	public void glRender(GL2 gl, double scale, boolean pickingMode)
	{
		gl.glPushAttrib(GL2.GL_COLOR);

		gl.glPushAttrib(GL2.GL_LIGHTING);
		gl.glDisable(GL2.GL_LIGHTING);

		gl.glPushAttrib(GL2.GL_DEPTH_TEST);
		gl.glDisable(GL2.GL_DEPTH_TEST);

		gl.glPushMatrix();

		transformationNode.glTranslateToOriginInWorldSpace(gl);

		gl.glScaled(scale, scale, scale);

		transformationNode.getLowestTransformationNodeAncestorBasisInWorldSpace(xAxis, yAxis, zAxis);

		gl.glPushMatrix();
		Util.glRotateYTo(gl,xAxis);
		gl.glColor4d(0.8, 0, 0, 1);
		if (pickingMode)
			gl.glLoadName(PICK_X);
		glRenderArrow(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		Util.glRotateYTo(gl,yAxis);
		gl.glColor4d(0, 0.8, 0, 1);
		if (pickingMode)
			gl.glLoadName(PICK_Y);
		glRenderArrow(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		Util.glRotateYTo(gl,zAxis);
		gl.glColor4d(0, 0, 0.8, 1);
		if (pickingMode)
			gl.glLoadName(PICK_Z);
		glRenderArrow(gl);
		gl.glPopMatrix();

		if (pickingMode)
			gl.glLoadName(PICK_CENTER);
		gl.glColor4d(0.8, 0.8, 0, 1);
		Util.glRenderBox(gl);

		gl.glPopMatrix();

		gl.glPopAttrib();
		gl.glPopAttrib();
		gl.glPopAttrib();
	}

	private static double arrowDivs = 32;
	private static double arrowTailRadius = 0.05;
	private static double arrowHeadRadius = 0.11;

	public static void glRenderArrow(GL2 gl) {
		glRenderArrow(Y_AXIS, gl);
	}

	public static void glRenderArrow(byte axis, GL2 gl) {

		gl.glPushMatrix();
		switch (axis) {
		case X_AXIS:
			gl.glRotatef(90f, 0, 0, -1);
			break;
		case Z_AXIS:
			gl.glRotatef(90f, 1, 0, 0);
		}
		// tail coney
		double theta = 0;
		gl.glBegin(GL.GL_TRIANGLE_FAN);
		gl.glVertex3d(0d, 0d, 0d);
		for (double i = 0; i <= arrowDivs; ++i) {
			theta = (i / arrowDivs) * Math.PI * 2;
			gl.glVertex3d(Math.cos(theta) * arrowTailRadius, 1.8, Math.sin(theta) * arrowTailRadius);
		}
		gl.glEnd();

		// neck ring
		gl.glBegin(GL2.GL_QUAD_STRIP);
		for (double i = 0; i <= arrowDivs; ++i) {
			theta = (i / arrowDivs) * Math.PI * 2;
			gl.glVertex3d(Math.cos(theta) * arrowTailRadius, 1.8, Math.sin(theta) * arrowTailRadius);
			gl.glVertex3d(Math.cos(theta) * arrowHeadRadius, 1.83, Math.sin(theta) * arrowHeadRadius);
		}
		gl.glEnd();

		// head coney
		gl.glBegin(GL.GL_TRIANGLE_FAN);
		gl.glVertex3d(0, 2, 0);
		for (double i = 0; i <= arrowDivs; ++i) {
			theta = (i / arrowDivs) * Math.PI * 2;
			gl.glVertex3d(Math.cos(theta) * arrowHeadRadius, 1.83, Math.sin(theta) * arrowHeadRadius);
		}
		gl.glEnd();

		gl.glPopMatrix();
	}
}
