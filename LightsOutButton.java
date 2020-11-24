package application;

import javafx.scene.control.ToggleButton;

public class LightsOutButton extends ToggleButton{
	
	private int col;
	private int row;
	
	public LightsOutButton(int row, int col) {
		super();
		this.col = col;
		this.row = row;
		setSelected(Math.random() > 0.5);
	}
	
	public LightsOutButton(int row, int col, boolean selected) {
		super();
		this.col = col;
		this.row = row;
		setSelected(selected);
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}
	
	public void toggle() {
		if (isSelected()) {
			setSelected(false);
		} else {
			setSelected(true);
		}
	}
	
	

}
