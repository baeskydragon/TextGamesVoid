package pathsType;

public class Character {

	private String name;
    private int health;
    private int attackPower;
    private String weapon;

    public Character(String name, int health, int attackPower, String weapon) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    

    /**
	 * @param attackPower the attackPower to set
	 */
	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	/**
	 * @param weapon the weapon to set
	 */
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	public int getAttackPower() {
        return attackPower;
    }

    public String getWeapon() {
        return weapon;
    }

    public void attack(Character target) {
        target.setHealth(target.getHealth() - this.attackPower);
        System.out.println(this.name + " attacks " + target.getName() + " with " + this.weapon + " for " + this.attackPower + " damage.");
    }
}

