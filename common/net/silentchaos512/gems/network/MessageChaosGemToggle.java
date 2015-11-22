package net.silentchaos512.gems.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.silentchaos512.gems.item.ChaosGem;

public class MessageChaosGemToggle implements IMessage {

  private boolean all;

  public MessageChaosGemToggle() {

  }

  public MessageChaosGemToggle(boolean all) {

    this.all = all;
  }

  @Override
  public void fromBytes(ByteBuf buf) {

    this.all = ByteBufUtils.readVarShort(buf) != 0;
  }

  @Override
  public void toBytes(ByteBuf buf) {

    ByteBufUtils.writeVarShort(buf, this.all ? 1 : 0);
  }

  public static class Handler implements IMessageHandler<MessageChaosGemToggle, IMessage> {

    @Override
    public IMessage onMessage(MessageChaosGemToggle message, MessageContext ctx) {

      if (ctx.side == Side.SERVER) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        // Find first chaos gem in player's inventory and toggle it.
        for (ItemStack stack : player.inventory.mainInventory) {
          if (stack != null && stack.getItem() instanceof ChaosGem) {
            ((ChaosGem) stack.getItem()).onItemRightClick(stack, player.worldObj, player);
            if (!message.all) {
              return null;
            }
          }
        }
      }

      return null;
    }
  }
}
