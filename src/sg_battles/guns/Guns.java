package sg_battles.guns;
import sg_battles.ships.Ship;

import java.awt.*;


public class Guns {
	private double[] position;
	private int damage;
	private int type;
	private double angle;
	private double speed;
	private int range;
	private int fireSpeed;
	private int[] shape;
	private Color color;
	private BasicStroke brush;
	private boolean guided;
	private Ship target = null;
	private Ship source = null;
	
	public double[] getPosition() {
		return position;
	}
	public void setPosition(double[] pozice) {
		this.position = pozice;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getFireSpeed() {
		return fireSpeed;
	}
	public void setFireSpeed(int fireSpeed) {
		this.fireSpeed = fireSpeed;
	}
	public int[] getShape() {
		return shape;
	}
	public void setShape(int[] shape) {
		this.shape = shape;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public BasicStroke getBrush() {
		return brush;
	}
	public void setBrush(BasicStroke brush) {
		this.brush = brush;
	}
	public boolean isGuided() {
		return guided;
	}
	public void setGuided(boolean guided) {
		this.guided = guided;
	}
	public Ship getTarget() {
		return target;
	}
	public void setTarget(Ship target) {
		this.target = target;
	}
	public Ship getSource() {
		return source;
	}
	public void setSource(Ship source) {
		this.source = source;
	}
}
