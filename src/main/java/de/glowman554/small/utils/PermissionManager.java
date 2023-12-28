package de.glowman554.small.utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.security.Permission;

public class PermissionManager {
    private final LuckPerms luckPerms = LuckPermsProvider.get();

    public void addPermission(HumanEntity player, String permission) {
        UserManager userManager = luckPerms.getUserManager();
        User user = userManager.getUser(player.getUniqueId());

        Node permissionNode = PermissionNode.builder(permission).build();

        user.data().add(permissionNode);
        userManager.saveUser(user);
    }

    public void removePermission(HumanEntity player, String permission) {
        UserManager userManager = luckPerms.getUserManager();
        User user = userManager.getUser(player.getUniqueId());

        user.data().remove(PermissionNode.builder(permission).build());
        userManager.saveUser(user);
    }
}
