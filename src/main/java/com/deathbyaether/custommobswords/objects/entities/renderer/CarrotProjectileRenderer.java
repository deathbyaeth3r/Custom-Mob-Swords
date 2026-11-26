package com.deathbyaether.custommobswords.objects.entities.renderer;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.objects.entities.CarrotProjectileEntity;
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
import net.minecraft.util.math.vector.Vector3f;

public class CarrotProjectileRenderer extends EntityRenderer<CarrotProjectileEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/carrot_projectile.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE);
	public CarrotProjectileRenderer(EntityRendererManager manager) { super(manager); }
	@Override public ResourceLocation getTextureLocation(CarrotProjectileEntity entity) { return TEXTURE; }
	@Override public void render(CarrotProjectileEntity entity, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		stack.pushPose();
		stack.scale(1.5F, 1.5F, 1.5F);
		stack.mulPose(entityRenderDispatcher.cameraOrientation());
		stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		MatrixStack.Entry entry = stack.last();
		Matrix4f pose = entry.pose(); Matrix3f normal = entry.normal();
		IVertexBuilder out = buffer.getBuffer(RENDER_TYPE);
		vertex(out, pose, normal, light, 0, 0, 0, 1);
		vertex(out, pose, normal, light, 1, 0, 1, 1);
		vertex(out, pose, normal, light, 1, 1, 1, 0);
		vertex(out, pose, normal, light, 0, 1, 0, 0);
		stack.popPose();
		super.render(entity, yaw, partialTicks, stack, buffer, light);
	}
	private static void vertex(IVertexBuilder out, Matrix4f pose, Matrix3f normal, int light, float u, int y, int texU, int texV) {
		out.vertex(pose, u - 0.5F, y - 0.25F, 0).color(255,255,255,255).uv(texU,texV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal,0,1,0).endVertex();
	}
}
