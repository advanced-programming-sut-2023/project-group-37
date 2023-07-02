package model.people;

import controller.MultiMenuFunctions;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DefensiveBuilding;
import model.game.Government;
import model.game.Tile;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class MilitaryUnit {

    private final Government loyalty;
    private int hitpoints;
    private final int damage;
    private final int range;

    // According to this.stance
    private int reaction_range;
    private final int speed;
    private Tile location;
    private LinkedList<Tile> route;
    private LinkedList<Tile> patrolRoute;
    private int patrolTile;
    private ArrayList<Tile> target;
    private ArrayList<Tile> moatTarget;
    private MilitaryUnitStance stance;
    private boolean hasTarget;
    private boolean hasMoatTarget;

    public MilitaryUnit(Government loyalty, Tile location, int hitpoints, int damage, int range, int speed) {
        this.loyalty = loyalty;
        this.location = location;
        this.location.addMilitaryUnit(this);
        this.loyalty.getMilitaryUnits().add(this);
        this.hitpoints = hitpoints;
        this.damage = damage;
        this.range = range;
        this.reaction_range = range;
        this.speed = speed;
        this.moatTarget = null;
        this.stance = MilitaryUnitStance.STANDING;
        this.hasTarget = false;
        this.hasMoatTarget = false;
    }

    public Government getLoyalty() {
        return this.loyalty;
    }

    public int getDamage() {
        return (int) (this.damage * (1 - (double) this.loyalty.getFearRate() / 6)) + 1;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public double getRange() {
        if (this.location.getBuilding() instanceof DefensiveBuilding defensiveBuilding)
            return defensiveBuilding.getDefensiveType().getRangeRate() * this.range;
        return this.range;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void attack() {
        if (hasTarget) {
            if (this instanceof MilitaryMachine) {
                if (((MilitaryMachine) this).getType() == MilitaryMachineType.FIRE_BALLISTA)
                    target.get(0).receiveDamage(this.getDamage(), this.loyalty);
                else if (((MilitaryMachine) this).getType() == MilitaryMachineType.SIEGE_TOWER) {
                    if (target.get(0).getBuilding() instanceof DefensiveBuilding defensiveBuilding) {
                        defensiveBuilding.setCanBeReached(true);
                        defensiveBuilding.setHasLadderAttached(true);
                    }
                } else if (((MilitaryMachine) this).getType() != MilitaryMachineType.PORTABLE_SHIELD)
                    target.get(0).receiveBuildingDamage(this.getDamage(), this.loyalty);
            } else {
                Troop troop = (Troop) this;
                if (troop.getType() == TroopType.LADDERMAN) {
                    if (target.get(0).getBuilding() instanceof DefensiveBuilding defensiveBuilding) {
                        defensiveBuilding.setCanBeReached(true);
                        defensiveBuilding.setHasLadderAttached(true);
                        this.location.getMilitaryUnits().remove(this);
                        this.loyalty.getMilitaryUnits().remove(this);
                    }
                } else if (troop.getType() == TroopType.TUNNELER) {
                    if (target.get(0).getBuilding() instanceof DefensiveBuilding defensiveBuilding) {
                        target.get(0).setPassability(true);
                        if (MultiMenuFunctions.routeFinder(this.location, target.get(0), location.getTerritory().getMap()) != null) {
                            defensiveBuilding.destroy();
                            this.location.getMilitaryUnits().remove(this);
                            this.target.get(0).removeBuilding();
                        }
                        target.get(0).setPassability(false);
                    }
                } else target.get(0).receiveDamage(this.getDamage(), this.loyalty);
            }
        }
    }

    public void stop() {
        this.hasTarget = false;
        this.target = new ArrayList<>();
        this.hasMoatTarget = false;
        this.moatTarget = new ArrayList<>();
        this.route = null;
        this.patrolRoute = null;
    }

    public int takeDamage(int damage) {
        int receivedDamage = Math.min(damage, this.hitpoints);
        hitpoints -= receivedDamage;

        return receivedDamage;
    }

    public Tile getLocation() {
        return this.location;
    }

    public String getName() {
        if (this instanceof Troop) {
            return ((Troop) this).getType().getName();
        } else {
            return ((MilitaryMachine) this).getType().getName();
        }
    }

    public MilitaryUnitStance getStance() {
        return this.stance;
    }

    public void setStance(MilitaryUnitStance stance) {
        this.stance = stance;

        if (stance == MilitaryUnitStance.DEFENSIVE)
            this.reaction_range = 2 * this.range;
        else if (stance == MilitaryUnitStance.AGGRESSIVE)
            this.reaction_range = 3 * this.range;
        else
            this.reaction_range = this.range;
    }

    public Tile getMoatTarget() {
        return this.moatTarget.get(0);
    }

    public void setMoatTarget(Tile moatTarget) {
        this.moatTarget = new ArrayList<>();
        this.moatTarget.add(moatTarget);
        hasMoatTarget = true;
    }

    public void cancelMoatTarget() {
        this.moatTarget = new ArrayList<>();
        this.hasMoatTarget = false;
    }

    public void move() {
        if (this.route.size() > 1) {
            LinkedList<Tile> route = this.getRoute();
            int speed = this.getSpeed();
            if (route.size() - 1 < speed)
                speed = route.size() - 1;

            boolean hasKillingPit = false;
            Building building;
            for (int i = 0; i <= speed; i++) {
                if ((building = route.get(i).getBuilding()) != null) {
                    if (building.getType() == BuildingType.KILLING_PIT) {
                        building.destroy();
                        hasKillingPit = true;
                        break;
                    }
                }
            }

            if (hasKillingPit) {
                this.loyalty.getMilitaryUnits().remove(this);
                this.location.getMilitaryUnits().remove(this);
            } else {
                this.location.getMilitaryUnits().remove(this);
                this.location = route.get(speed);
                this.location.addMilitaryUnit(this);

                if (speed > 0)
                    this.route.subList(0, speed).clear();
            }
        }
    }

    public void setRoute(LinkedList<Tile> route) {
        this.route = route;
        this.target = new ArrayList<>();
    }

    public boolean isOnAttack() {
        if (this.target == null)
            return false;
        return this.target.size() != 0;
    }

    public void setPatrol(LinkedList<Tile> patrolRoute) {
        this.patrolRoute = patrolRoute;
        this.patrolTile = 1;
    }

    public void setTarget(Tile target) {
        this.target = new ArrayList<>();
        this.target.add(target);
        hasTarget = true;
    }

    protected LinkedList<Tile> getRoute() {
        return this.route;
    }

    public boolean isOnMove() {
        if (route == null)
            return false;

        return this.route.size() > 1;
    }

    public boolean isOnPatrol() {
        if (isOnMove())
            return false;

        if (patrolRoute == null)
            return false;

        return this.patrolRoute.size() > 1;
    }

    public void cancelPatrol() {
        this.patrolRoute = null;
        this.patrolTile = -1;
    }

    public void cancelMove() {
        this.route = null;
    }

    private int getIndexByTile(Tile tile) {
        Tile target;
        for (int i = 0; i < patrolRoute.size(); i++) {
            target = patrolRoute.get(i);
            if (target.equals(tile))
                return i;
        }
        return 0;
    }

    public void patrol() {
        int speed = this.speed;
        int index = getIndexByTile(location);

        if (patrolTile == 1) {
            if (speed > patrolRoute.size() - 1 - index)
                speed = patrolRoute.size() - 1 - index;

            index += speed;
            this.location = patrolRoute.get(index);

            if (index == patrolRoute.size() - 1)
                patrolTile = 0;
        } else if (patrolTile == 0) {
            if (speed > index)
                speed = index;

            index -= speed;
            this.location = patrolRoute.get(index);

            if (index == 0)
                patrolTile = 1;
        }
    }

    public abstract boolean canGoUp();

    public boolean hasMoatTarget() {
        return hasMoatTarget;
    }

}