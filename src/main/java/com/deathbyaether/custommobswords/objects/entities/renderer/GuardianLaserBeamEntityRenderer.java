package com.deathbyaether.custommobswords.objects.entities.renderer;

import com.deathbyaether.custommobswords.objects.entities.GuardianLaserBeamEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;

public class GuardianLaserBeamEntityRenderer extends EntityRenderer<GuardianLaserBeamEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/guardian_beam.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE);
	public GuardianLaserBeamEntityRenderer(EntityRendererManager manager) { super(manager); }
	@Override public ResourceLocation getTextureLocation(GuardianLaserBeamEntity entity) { return TEXTURE; }

	@Override
	public void render(GuardianLaserBeamEntity entity, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		if (entity.getShooter() == null) return;
		Vector3d start = entity.getShooter().position().add(0, entity.getShooter().getEyeHeight(), 0);
		Vector3d end = entity.position();
		Vector3d axis = end.subtract(start);
		double length = axis.length();
		if (length < 0.01D) return;
		Vector3d direction = axis.scale(1.0D / length);
		Vector3d reference = Math.abs(direction.y) < 0.9D ? new Vector3d(0,1,0) : new Vector3d(1,0,0);
		Vector3d side = direction.cross(reference).normalize().scale(0.17D);
		Vector3d up = direction.cross(side).normalize().scale(0.17D);
		Vector3d[] ring = { side.add(up), side.scale(-1).add(up), side.scale(-1).subtract(up), side.subtract(up) };
		stack.pushPose();
		stack.translate(start.x - entity.getX(), start.y - entity.getY(), start.z - entity.getZ());
		MatrixStack.Entry entry = stack.last();
		Matrix4f pose = entry.pose(); Matrix3f normal = entry.normal();
		IVertexBuilder out = buffer.getBuffer(RENDER_TYPE);
		for (int i = 0; i < 4; i++) {
			Vector3d a = ring[i], b = ring[(i + 1) % 4];
			quad(out, pose, normal, light, a, b, axis, i);
		}
		stack.popPose();
		super.render(entity, yaw, partialTicks, stack, buffer, light);
	}

	private static void quad(IVertexBuilder out, Matrix4f pose, Matrix3f normal, int light, Vector3d a, Vector3d b, Vector3d axis, int side) {
		float u0 = side / 4.0F, u1 = (side + 1) / 4.0F;
		v(out,pose,normal,light,a,0,u0); v(out,pose,normal,light,b,0,u1);
		v(out,pose,normal,light,b.add(axis),1,u1); v(out,pose,normal,light,a.add(axis),1,u0);
	}
	private static void v(IVertexBuilder out, Matrix4f pose, Matrix3f normal, int light, Vector3d p, float v, float u) {
		out.vertex(pose,(float)p.x,(float)p.y,(float)p.z).color(255,255,255,255).uv(u,v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal,0,1,0).endVertex();
	}
}
