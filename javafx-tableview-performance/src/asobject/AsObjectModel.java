package asobject;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class AsObjectModel {

	private final ReadOnlyIntegerProperty anInt;
	private final ReadOnlyObjectProperty<Integer> anIntAsObject;

	private final ReadOnlyDoubleProperty aDouble;
	private final ReadOnlyObjectProperty<Double> aDoubleAsObject;

	public AsObjectModel(final int anInt, final double aDouble) {
		this.anInt = new SimpleIntegerProperty(anInt);
		this.anIntAsObject = this.anInt.asObject();

		this.aDouble = new SimpleDoubleProperty(aDouble);
		this.aDoubleAsObject = this.aDouble.asObject();
	}

	public ReadOnlyIntegerProperty anInt() {
		return anInt;
	}

	public ReadOnlyObjectProperty<Integer> anIntAsObject() {
		return anIntAsObject;
	}

	public ReadOnlyDoubleProperty aDouble() {
		return aDouble;
	}

	public ReadOnlyObjectProperty<Double> aDoubleAsObject() {
		return aDoubleAsObject;
	}
}
