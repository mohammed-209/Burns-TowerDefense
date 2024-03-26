package edu.comp55.burnstd.map;

public class Location {
	private int row;
	private int col;
	private float x, y;
	private float degreesFacing;

	public Location(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	@Override
	public String toString() {
		return "r" + getRow() + "c" + getCol();
	}

	public static void main(String[] args) { // for testing
		Location one = new Location(3, 4);
		Location two = new Location(1, 6);
		two.setRow(two.getRow() + 1);
		two.setCol(two.getCol() - 1);

		System.out.println(one);
	}
}
