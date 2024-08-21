package hairDressingManagement;

import java.util.Arrays;
import java.util.Date;

import simesterB.HairDresser;
import simesterB.HairDresserIsExsitsException;
import simesterB.Treatment;

public class Shift implements Comparable<Shift> {
	private final int COFFEE_COST = 10;

	private HairDresser[] hairDressers;
	private Treatment[] treatments;
	private int countOfHairdre;
	private Date dateStart;
	private int durationHours;

	public Shift(Date dateStart, int durationHours) {
		this.dateStart = dateStart;
		this.durationHours = durationHours;
		treatments = new Treatment[0];
		hairDressers = new HairDresser[0];
	}

	public HairDresser[] getHairDre() {
		return hairDressers;
	}

	public int getCountOfHairdre() {
		return countOfHairdre;
	}

	public int getDuration() {
		return durationHours;
	}

	public Date getDate() {
		return dateStart;
	}

	public Treatment[] getTreatments() {
		return treatments;
	}

	public void addHairDresser(HairDresser hairDresser) throws HairDresserIsExsitsException {
		hairDressers = Arrays.copyOf(hairDressers, hairDressers.length + 1);
		if (hairDresserIsExsits(hairDressers, hairDresser)) {
			throw new HairDresserIsExsitsException("Is exsits");
		}
		hairDressers[hairDressers.length - 1] = hairDresser;

	}

	public void addTreatment(Treatment treatment) {
		treatments = Arrays.copyOf(treatments, treatments.length + 1);
		treatments[treatments.length - 1] = treatment;
		sortTreatmentsByHour();

	}

	public void sortTreatmentsByHour() {
		Arrays.sort(treatments);
	}

	@Override
	public int compareTo(Shift o) {
		return this.dateStart.compareTo(o.dateStart);
	}

	public int calaProfit() {
		int amount = 0;
		for (int i = 0; i < treatments.length; i++) {
			amount += treatments[i].getPayment();
		}
		for (int i = 0; i < hairDressers.length; i++) {
			if (hairDressers[i].isVip()) {
				amount -= COFFEE_COST;
			}

		}
		for (int i = 0; i < treatments.length; i++) {
			if (treatments[i].getClientDetails().isVip()) {
				amount -= COFFEE_COST;
			}
		}
		return amount;
	}

	public boolean hairDresserIsExsits(HairDresser[] hairDressers, HairDresser hairDresser) {
		for (int i = 0; i < hairDressers.length; i++) {
			if (hairDressers[i].getName() == hairDresser.getName()) {
				return true;
			}

		}
		return false;

	}
}
