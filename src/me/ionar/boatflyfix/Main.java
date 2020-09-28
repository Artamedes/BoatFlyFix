package me.ionar.boatflyfix;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;
import com.comphenix.protocol.wrappers.EnumWrappers.Hand;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                try {
                    PacketContainer packet = event.getPacket();
                    Entity target = packet.getEntityModifier(player.getWorld()).read(0);
                    EntityUseAction action = packet.getEntityUseActions().read(0);
                    Hand hand = packet.getHands().read(0);
                    if (action == EntityUseAction.INTERACT && target instanceof Boat && hand == Hand.MAIN_HAND) {
                        Boat boat = (Boat)target;
                        if (boat.getPassengers().contains(player)) {
                            System.out.println("Player: " + player.getName() + " tried to use BoatFly!");
                            event.setCancelled(true);
                        }
                    }
                } catch (Exception e) {
                    
                }
            }
        });
    }
}
