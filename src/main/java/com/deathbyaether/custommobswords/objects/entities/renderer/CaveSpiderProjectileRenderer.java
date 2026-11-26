package com.deathbyaether.custommobswords.objects.entities.renderer;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.objects.entities.CaveSpiderProjectileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class CaveSpiderProjectileRenderer extends EntityRenderer<CaveSpiderProjectileEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/cavespider_projectile.png");
	private static final RenderType RENDER_TYPE = RenderType.getEntityCutoutNoCull(TEXTURE);
	public CaveSpiderProjectileRenderer(EntityRendererManager manager) { super(manager); }
	@Override public ResourceLocation getEntityTexture(CaveSpiderProjectileEntity entity) { return TEXTURE; }
	@Override public void render(CaveSpiderProjectileEntity entity, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		stack.push(); stack.scale(1.8F, 1.8F, 1.8F); stack.rotate(renderManager.getCameraOrientation()); stack.rotate(Vector3f.YP.rotationDegrees(180.0F));
		MatrixStack.Entry entry = stack.getLast(); Matrix4f pose = entry.getMatrix(); Matrix3f normal = entry.getNormal(); IVertexBuilder out = buffer.getBuffer(RENDER_TYPE);
		vertex(out,pose,normal,light,0,0,0,1); vertex(out,pose,normal,light,1,0,1,1); vertex(out,pose,normal,light,1,1,1,0); vertex(out,pose,normal,light,0,1,0,0);
		stack.pop(); super.render(entity,yaw,partialTicks,stack,buffer,light);
	}
	private static void vertex(IVertexBuilder out, Matrix4f pose, Matrix3f normal, int light, float u, int y, int texU, int texV) {
		out.pos(pose,u-0.5F,y-0.25F,0).color(255,255,255,255).tex(texU,texV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(normal,0,1,0).endVertex();
	}
}
