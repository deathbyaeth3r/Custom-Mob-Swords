package com.deathbyaether.custommobswords.objects.entities.renderer;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.objects.entities.CaveSpiderProjectileEntity;
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

public class CaveSpiderProjectileRenderer extends EntityRenderer<CaveSpiderProjectileEntity> {

    public static final ResourceLocation CAVESPIDER_ENTITY = new ResourceLocation(Main.MOD_ID, "textures/entity/cavespider_projectile.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(CAVESPIDER_ENTITY);
    public CaveSpiderProjectileRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }
    
    protected int getBlockLight(CaveSpiderProjectileRenderer entityIn, float partialTicks) {
        return 15;
     }

     public void render(CaveSpiderProjectileEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.scale(2.0F, 2.0F, 2.0F);
        matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        MatrixStack.Entry matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RENDER_TYPE);
        vertex(ivertexbuilder, matrix4f, matrix3f, packedLightIn, 0.0F, 0, 0, 1);
        vertex(ivertexbuilder, matrix4f, matrix3f, packedLightIn, 1.0F, 0, 1, 1);
        vertex(ivertexbuilder, matrix4f, matrix3f, packedLightIn, 1.0F, 1, 1, 0);
        vertex(ivertexbuilder, matrix4f, matrix3f, packedLightIn, 0.0F, 1, 0, 0);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
     }

     private static void vertex(IVertexBuilder vertexBuilder, Matrix4f p_229045_1_, Matrix3f p_229045_2_, int p_229045_3_, float x, int y, int u, int v) {
        vertexBuilder.vertex(p_229045_1_, x - 0.5F, (float)y - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)u, (float)v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_229045_3_).normal(p_229045_2_, 0.0F, 1.0F, 0.0F).endVertex();
     }

	@Override
	public ResourceLocation getTextureLocation(CaveSpiderProjectileEntity entity) {
		
		return CAVESPIDER_ENTITY;
	}

}
