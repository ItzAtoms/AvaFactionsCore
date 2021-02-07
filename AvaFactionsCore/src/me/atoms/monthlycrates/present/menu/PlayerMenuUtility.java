package me.atoms.monthlycrates.present.menu;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {

	private Player owner;
	
	private String recipient;
	
	private String presentMessage;
	
	public PlayerMenuUtility(Player p) {
		this.setOwner(p);
	}

	/**
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}

	/**
	 * @param recipient the recipient to set
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	/**
	 * @return the presentMessage
	 */
	public String getPresentMessage() {
		return presentMessage;
	}

	/**
	 * @param presentMessage the presentMessage to set
	 */
	public void setPresentMessage(String presentMessage) {
		this.presentMessage = presentMessage;
	}
}
