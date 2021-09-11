package alkalus.main.asm.transformer;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.DCMPL;
import static org.objectweb.asm.Opcodes.DLOAD;
import static org.objectweb.asm.Opcodes.DMUL;
import static org.objectweb.asm.Opcodes.DSTORE;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.IFLE;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.INSTANCEOF;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.RETURN;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassTransformer_Witchery_PacketPipeline extends BaseTransformer {

	public ClassTransformer_Witchery_PacketPipeline(String transformedName, byte[] basicClass, boolean isObf) {
		super(transformedName, basicClass, isObf);
		init();
	}	

	public final String aEntityPlayer = mObf ? "yz" : "net/minecraft/entity/player/EntityPlayer";
	public final String aEntityPlayerMP = mObf ? "mw" : "net/minecraft/entity/player/EntityPlayerMP";
	public final String aPacket = mObf ? "ft" : "net/minecraft/network/Packet";
	public final String aWorld = mObf ? "ahb" : "net/minecraft/world/World";
	public final String aEntity = mObf ? "sa" : "net/minecraft/entity/Entity";
	public final String aNetHandlerPlayServer = mObf ? "nh" : "net/minecraft/network/NetHandlerPlayServer";

	public boolean injectMethod(String aMethodName, ClassWriter cw) {
		boolean[] aResults = new boolean[4];		
		aResults[0] = inject_sendTo1();	
		aResults[1] = inject_sendTo2();	
		aResults[2] = inject_sendToDimension();
		aResults[3] = inject_sendToAllAround();
		for (boolean b : aResults) {
			if (!b) {
				log("Patching failed.");
				return false;
			}
		}	
		log("Patching Success.");	
		return true;
	}


	private boolean inject_sendTo1() {
		MethodVisitor mv;
		mv = getWriter().visitMethod(ACC_PUBLIC, "sendTo", "(Lcpw/mods/fml/common/network/simpleimpl/IMessage;L"+aEntityPlayerMP+";)V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(51, l0);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitMethodInsn(INVOKESTATIC, "alkalus/main/core/util/FakePlayerUtils", "isRealPlayer", "(L"+aEntity+";)Z", false);
		Label l1 = new Label();
		mv.visitJumpInsn(IFEQ, l1);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitLineNumber(52, l2);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "com/emoniph/witchery/network/PacketPipeline", "CHANNEL", "Lcpw/mods/fml/common/network/simpleimpl/SimpleNetworkWrapper;");
		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitMethodInsn(INVOKEVIRTUAL, "cpw/mods/fml/common/network/simpleimpl/SimpleNetworkWrapper", "sendTo", "(Lcpw/mods/fml/common/network/simpleimpl/IMessage;L"+aEntityPlayerMP+";)V", false);
		mv.visitLabel(l1);
		mv.visitLineNumber(54, l1);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitInsn(RETURN);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitLocalVariable("this", "Lcom/emoniph/witchery/network/PacketPipeline;", null, l0, l3, 0);
		mv.visitLocalVariable("message", "Lcpw/mods/fml/common/network/simpleimpl/IMessage;", null, l0, l3, 1);
		mv.visitLocalVariable("player", "L"+aEntityPlayerMP+";", null, l0, l3, 2);
		mv.visitMaxs(3, 3);
		mv.visitEnd();
		return true;
	}
	
	private boolean inject_sendTo2() {
		MethodVisitor mv;
		mv = getWriter().visitMethod(ACC_PUBLIC, "sendTo", "(L"+aPacket+";L"+aEntityPlayer+";)V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(73, l0);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitMethodInsn(INVOKESTATIC, "alkalus/main/core/util/FakePlayerUtils", "isRealPlayer", "(L"+aEntity+";)Z", false);
		Label l1 = new Label();
		mv.visitJumpInsn(IFEQ, l1);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitTypeInsn(INSTANCEOF, ""+aEntityPlayerMP+"");
		mv.visitJumpInsn(IFEQ, l1);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitLineNumber(74, l2);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitTypeInsn(CHECKCAST, ""+aEntityPlayerMP+"");
		mv.visitVarInsn(ASTORE, 3);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitLineNumber(75, l3);
		mv.visitVarInsn(ALOAD, 3);
		mv.visitFieldInsn(GETFIELD, ""+aEntityPlayerMP+"", "playerNetServerHandler", "L"+aNetHandlerPlayServer+";");
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKEVIRTUAL, ""+aNetHandlerPlayServer+"", "sendPacket", "(L"+aPacket+";)V", false);
		mv.visitLabel(l1);
		mv.visitLineNumber(77, l1);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitInsn(RETURN);
		Label l4 = new Label();
		mv.visitLabel(l4);
		mv.visitLocalVariable("this", "Lcom/emoniph/witchery/network/PacketPipeline;", null, l0, l4, 0);
		mv.visitLocalVariable("packet", "L"+aPacket+";", null, l0, l4, 1);
		mv.visitLocalVariable("player", "L"+aEntityPlayer+";", null, l0, l4, 2);
		mv.visitLocalVariable("mp", "L"+aEntityPlayerMP+";", null, l3, l1, 3);
		mv.visitMaxs(2, 4);
		mv.visitEnd();
		return true;
	}
	
	private boolean inject_sendToDimension() {
		MethodVisitor mv;
		mv = getWriter().visitMethod(ACC_PUBLIC, "sendToDimension", "(L"+aPacket+";L"+aWorld+";)V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(80, l0);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitFieldInsn(GETFIELD, ""+aWorld+"", "playerEntities", "Ljava/util/List;");
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "iterator", "()Ljava/util/Iterator;", true);
		mv.visitVarInsn(ASTORE, 4);
		Label l1 = new Label();
		mv.visitJumpInsn(GOTO, l1);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitFrame(Opcodes.F_FULL, 5, new Object[] {"com/emoniph/witchery/network/PacketPipeline", ""+aPacket+"", ""+aWorld+"", Opcodes.TOP, "java/util/Iterator"}, 0, new Object[] {});
		mv.visitVarInsn(ALOAD, 4);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
		mv.visitVarInsn(ASTORE, 3);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitLineNumber(81, l3);
		mv.visitVarInsn(ALOAD, 3);
		mv.visitTypeInsn(INSTANCEOF, ""+aEntityPlayerMP+"");
		mv.visitJumpInsn(IFEQ, l1);
		Label l4 = new Label();
		mv.visitLabel(l4);
		mv.visitLineNumber(82, l4);
		mv.visitVarInsn(ALOAD, 3);
		mv.visitTypeInsn(CHECKCAST, ""+aEntityPlayerMP+"");
		mv.visitVarInsn(ASTORE, 5);
		Label l5 = new Label();
		mv.visitLabel(l5);
		mv.visitLineNumber(83, l5);
		mv.visitVarInsn(ALOAD, 5);
		mv.visitMethodInsn(INVOKESTATIC, "alkalus/main/core/util/FakePlayerUtils", "isRealPlayer", "(L"+aEntity+";)Z", false);
		mv.visitJumpInsn(IFEQ, l1);
		Label l6 = new Label();
		mv.visitLabel(l6);
		mv.visitLineNumber(84, l6);
		mv.visitVarInsn(ALOAD, 5);
		mv.visitFieldInsn(GETFIELD, ""+aEntityPlayerMP+"", "playerNetServerHandler", "L"+aNetHandlerPlayServer+";");
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKEVIRTUAL, ""+aNetHandlerPlayServer+"", "sendPacket", "(L"+aPacket+";)V", false);
		mv.visitLabel(l1);
		mv.visitLineNumber(80, l1);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitVarInsn(ALOAD, 4);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
		mv.visitJumpInsn(IFNE, l2);
		Label l7 = new Label();
		mv.visitLabel(l7);
		mv.visitLineNumber(88, l7);
		mv.visitInsn(RETURN);
		Label l8 = new Label();
		mv.visitLabel(l8);
		mv.visitLocalVariable("this", "Lcom/emoniph/witchery/network/PacketPipeline;", null, l0, l8, 0);
		mv.visitLocalVariable("packet", "L"+aPacket+";", null, l0, l8, 1);
		mv.visitLocalVariable("world", "L"+aWorld+";", null, l0, l8, 2);
		mv.visitLocalVariable("obj", "Ljava/lang/Object;", null, l3, l1, 3);
		mv.visitLocalVariable("mp", "L"+aEntityPlayerMP+";", null, l5, l1, 5);
		mv.visitMaxs(2, 6);
		mv.visitEnd();
		return true;
	}
	
	private boolean inject_sendToAllAround() {
		MethodVisitor mv;
		mv = getWriter().visitMethod(ACC_PUBLIC, "sendToAllAround", "(L"+aPacket+";L"+aWorld+";Lcpw/mods/fml/common/network/NetworkRegistry$TargetPoint;)V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(97, l0);
		mv.visitVarInsn(ALOAD, 3);
		mv.visitFieldInsn(GETFIELD, "cpw/mods/fml/common/network/NetworkRegistry$TargetPoint", "range", "D");
		mv.visitVarInsn(ALOAD, 3);
		mv.visitFieldInsn(GETFIELD, "cpw/mods/fml/common/network/NetworkRegistry$TargetPoint", "range", "D");
		mv.visitInsn(DMUL);
		mv.visitVarInsn(DSTORE, 4);
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitLineNumber(98, l1);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitFieldInsn(GETFIELD, ""+aWorld+"", "playerEntities", "Ljava/util/List;");
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "iterator", "()Ljava/util/Iterator;", true);
		mv.visitVarInsn(ASTORE, 7);
		Label l2 = new Label();
		mv.visitJumpInsn(GOTO, l2);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitFrame(Opcodes.F_FULL, 7, new Object[] {"com/emoniph/witchery/network/PacketPipeline", ""+aPacket+"", ""+aWorld+"", "cpw/mods/fml/common/network/NetworkRegistry$TargetPoint", Opcodes.DOUBLE, Opcodes.TOP, "java/util/Iterator"}, 0, new Object[] {});
		mv.visitVarInsn(ALOAD, 7);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
		mv.visitVarInsn(ASTORE, 6);
		Label l4 = new Label();
		mv.visitLabel(l4);
		mv.visitLineNumber(99, l4);
		mv.visitVarInsn(ALOAD, 6);
		mv.visitTypeInsn(INSTANCEOF, ""+aEntityPlayerMP+"");
		mv.visitJumpInsn(IFEQ, l2);
		Label l5 = new Label();
		mv.visitLabel(l5);
		mv.visitLineNumber(100, l5);
		mv.visitVarInsn(ALOAD, 6);
		mv.visitTypeInsn(CHECKCAST, ""+aEntityPlayerMP+"");
		mv.visitVarInsn(ASTORE, 8);
		Label l6 = new Label();
		mv.visitLabel(l6);
		mv.visitLineNumber(101, l6);
		mv.visitVarInsn(ALOAD, 8);
		mv.visitMethodInsn(INVOKESTATIC, "alkalus/main/core/util/FakePlayerUtils", "isRealPlayer", "(L"+aEntity+";)Z", false);
		mv.visitJumpInsn(IFEQ, l2);
		Label l7 = new Label();
		mv.visitLabel(l7);
		mv.visitLineNumber(102, l7);
		mv.visitVarInsn(ALOAD, 8);
		mv.visitVarInsn(ALOAD, 3);
		mv.visitFieldInsn(GETFIELD, "cpw/mods/fml/common/network/NetworkRegistry$TargetPoint", "x", "D");
		mv.visitVarInsn(ALOAD, 3);
		mv.visitFieldInsn(GETFIELD, "cpw/mods/fml/common/network/NetworkRegistry$TargetPoint", "y", "D");
		mv.visitVarInsn(ALOAD, 3);
		mv.visitFieldInsn(GETFIELD, "cpw/mods/fml/common/network/NetworkRegistry$TargetPoint", "z", "D");
		mv.visitMethodInsn(INVOKEVIRTUAL, ""+aEntityPlayerMP+"", "getDistanceSq", "(DDD)D", false);
		mv.visitVarInsn(DLOAD, 4);
		mv.visitInsn(DCMPL);
		Label l8 = new Label();
		mv.visitJumpInsn(IFLE, l8);
		Label l9 = new Label();
		mv.visitLabel(l9);
		mv.visitLineNumber(103, l9);
		mv.visitJumpInsn(GOTO, l2);
		mv.visitLabel(l8);
		mv.visitLineNumber(105, l8);
		mv.visitFrame(Opcodes.F_FULL, 8, new Object[] {"com/emoniph/witchery/network/PacketPipeline", ""+aPacket+"", ""+aWorld+"", "cpw/mods/fml/common/network/NetworkRegistry$TargetPoint", Opcodes.DOUBLE, "java/lang/Object", "java/util/Iterator", ""+aEntityPlayerMP+""}, 0, new Object[] {});
		mv.visitVarInsn(ALOAD, 8);
		mv.visitFieldInsn(GETFIELD, ""+aEntityPlayerMP+"", "playerNetServerHandler", "L"+aNetHandlerPlayServer+";");
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKEVIRTUAL, ""+aNetHandlerPlayServer+"", "sendPacket", "(L"+aPacket+";)V", false);
		mv.visitLabel(l2);
		mv.visitLineNumber(98, l2);
		mv.visitFrame(Opcodes.F_FULL, 7, new Object[] {"com/emoniph/witchery/network/PacketPipeline", ""+aPacket+"", ""+aWorld+"", "cpw/mods/fml/common/network/NetworkRegistry$TargetPoint", Opcodes.DOUBLE, Opcodes.TOP, "java/util/Iterator"}, 0, new Object[] {});
		mv.visitVarInsn(ALOAD, 7);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
		mv.visitJumpInsn(IFNE, l3);
		Label l10 = new Label();
		mv.visitLabel(l10);
		mv.visitLineNumber(109, l10);
		mv.visitInsn(RETURN);
		Label l11 = new Label();
		mv.visitLabel(l11);
		mv.visitLocalVariable("this", "Lcom/emoniph/witchery/network/PacketPipeline;", null, l0, l11, 0);
		mv.visitLocalVariable("packet", "L"+aPacket+";", null, l0, l11, 1);
		mv.visitLocalVariable("world", "L"+aWorld+";", null, l0, l11, 2);
		mv.visitLocalVariable("point", "Lcpw/mods/fml/common/network/NetworkRegistry$TargetPoint;", null, l0, l11, 3);
		mv.visitLocalVariable("RANGE_SQ", "D", null, l1, l11, 4);
		mv.visitLocalVariable("obj", "Ljava/lang/Object;", null, l4, l2, 6);
		mv.visitLocalVariable("mp", "L"+aEntityPlayerMP+";", null, l6, l2, 8);
		mv.visitMaxs(7, 9);
		mv.visitEnd();
		return true;
	}





	
	//public void sendTo(final IMessage message, final EntityPlayerMP/mw player)
	//public void sendTo(final Packet/ft packet, final EntityPlayer/yz player)
	//public void sendToDimension(final Packet packet, final World/ahb world)
	//public void sendToAllAround(final Packet packet, final World world, final NetworkRegistry.TargetPoint point)

	@Override
	public String[] getMethodNamesToStrip() {
		return new String[] {
				"sendTo",
				"sendToDimension",
				"sendToAllAround",
		};
	}

	@Override
	public String[] getMethodDescriptorsToStrip() {
		return new String[] {				
				"(Lcpw/mods/fml/common/network/simpleimpl/IMessage;L"+aEntityPlayerMP+";)V",
				"(L"+aPacket+";L"+aEntityPlayer+";)V",
				"(L"+aPacket+";L"+aWorld+";)V",
				"(L"+aPacket+";L"+aWorld+";Lcpw/mods/fml/common/network/NetworkRegistry$TargetPoint;)V",
		};
	}

	@Override
	public String getTransformerName() {
		return "PacketPipeline patch";
	}

	@Override
	protected boolean runInjectorMultipleTimes() {
		return false;
	}

}
