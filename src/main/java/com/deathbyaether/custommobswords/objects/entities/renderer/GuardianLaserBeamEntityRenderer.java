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
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;

/** Renders the travelling beam with Minecraft's vanilla guardian beam texture. */
public class GuardianLaserBeamEntityRenderer extends EntityRenderer<GuardianLaserBeamEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/guardian_beam.png");
	private static final RenderType RENDER_TYPE = RenderType.getEntityCutoutNoCull(TEXTURE);

	public GuardianLaserBeamEntityRenderer(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getEntityTexture(GuardianLaserBeamEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(GuardianLaserBeamEntity beam, float yaw, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer buffer, int light) {
		if (beam.getShooter() == null) return;
		Vec3d start = beam.getShooter().getPositionVec().add(0.0D, beam.getShooter().getEyeHeight(), 0.0D);
		Vec3d axis = beam.getPositionVec().subtract(start);
		double length = axis.length();
		if (length < 0.01D) return;

		Vec3d direction = axis.scale(1.0D / length);
		Vec3d reference = Math.abs(direction.y) < 0.9D ? new Vec3d(0, 1, 0) : new Vec3d(1, 0, 0);
		Vec3d side = direction.crossProduct(reference).normalize().scale(0.17D);
		Vec3d up = direction.crossProduct(side).normalize().scale(0.17D);
		Vec3d[] ring = { side.add(up), side.scale(-1).add(up), side.scale(-1).subtract(up), side.subtract(up) };

		stack.push();
		stack.translate(start.x - beam.getPosX(), start.y - beam.getPosY(), start.z - beam.getPosZ());
		MatrixStack.Entry entry = stack.getLast();
		Matrix4f pose = entry.getMatrix();
		Matrix3f normal = entry.getNormal();
		IVertexBuilder out = buffer.getBuffer(RENDER_TYPE);
		for (int i = 0; i < 4; i++) {
			Vec3d a = ring[i], b = ring[(i + 1) % 4];
			float u0 = i / 4.0F, u1 = (i + 1) / 4.0F;
			vertex(out, pose, normal, light, a, 0.0F, u0);
			vertex(out, pose, normal, light, b, 0.0F, u1);
			vertex(out, pose, normal, light, b.add(axis), 1.0F, u1);
			vertex(out, pose, normal, light, a.add(axis), 1.0F, u0);
		}
		stack.pop();
	}

	private static void vertex(IVertexBuilder out, Matrix4f pose, Matrix3f normal, int light,
			Vec3d point, float v, float u) {
		out.pos(pose, (float) point.x, (float) point.y, (float) point.z)
				.color(255, 255, 255, 255).tex(u, v).overlay(OverlayTexture.NO_OVERLAY)
				.lightmap(light).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
	}
}
