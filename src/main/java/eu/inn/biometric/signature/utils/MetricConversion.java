package eu.inn.biometric.signature.utils;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * MetricConversion.java is part of BioSignIn project
 * %%
 * Copyright (C) 2014 Innovery SpA
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */


import eu.inn.biometric.signature.device.MetricUnits;

public class MetricConversion {
	public final double MM_PER_INCH = 25.4;
	public final double PDFUNITS_PER_INCH = 72.0;

	private static double InchToPdfUnit(double inch) {
		return (inch * 72.0);
	}

	private static double MmToInch(double mm) {
		return (mm / 25.4);
	}

	public static double ToPdfUnits(double sourceValue, MetricUnits sourceUnit) throws Exception {
		switch (sourceUnit) {
		case PdfUnits:
			return sourceValue;
		case Millimeters:
			return InchToPdfUnit(MmToInch(sourceValue));
		case Inches:
			return InchToPdfUnit(sourceValue);
		case Points:
			return sourceValue;
		default:
		}
		throw new UnsupportedOperationException(sourceUnit.toString());
	}
}
