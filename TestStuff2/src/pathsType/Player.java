package pathsType;

/**
 * 
 * 
 * 
 */
public class Player extends Character {
    private String domain;
	private int attackPower;
	private String name;
	private String weapon;
    

    public Player(String name, int health, int attackPower, String weapon, String domain) {
        super(name, health, attackPower, weapon);
        this.domain = domain;
        this.attackPower = attackPower;
    }

    public String getDomain() {
        return domain;
    }
    //

    public void useDomainAbility() {
        switch (domain) {
            case "Healing":
                this.setHealth(this.getHealth() + 20); // Example healing amount
                System.out.println(this.getName() + " uses Healing! Health is now " + this.getHealth());
                break;
            case "Rage":
                this.setAttackPower(this.getAttackPower() + 10); // Example rage boost
                System.out.println(this.getName() + " uses Rage! Attack power is now " + this.getAttackPower());
                break;
            case "Spirit":
                System.out.println(this.getName() + " uses Spirit! You can go back to your last choice.");
                // Implement logic to go back to the last choice
                break;
            default:
                System.out.println("No domain ability available.");
                break;
        }
    }
}
