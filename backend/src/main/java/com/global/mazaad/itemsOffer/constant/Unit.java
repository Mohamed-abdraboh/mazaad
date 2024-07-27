package com.global.mazaad.itemsOffer.constant;

public enum Unit {
  GRAM("g"),
  KILOGRAM("kg"),
  TON("ton"),
  LITER("l"),
  MILLILITER("ml"),
  PIECE("pcs"),
  METER("m"),
  CENTIMETER("cm"),
  SQUARE_METER("m²"),
  CUBIC_METER("m³"),
  GALLON("gal"),
  YARD("yd"),
  FOOT("ft"),
  INCH("in"),
  POUND("lb"),
  OUNCE("oz");

  private final String symbol;

  Unit(String symbol) {
    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }
}
