package edu.unl.cse.csce361.package_tracker.logic;

import edu.unl.cse.csce361.package_tracker.backend.Address;
import edu.unl.cse.csce361.package_tracker.backend.BackendFacade;
import edu.unl.cse.csce361.package_tracker.backend.Sender;
import edu.unl.cse.csce361.package_tracker.frontend.Printer;
import edu.unl.cse.csce361.package_tracker.frontend.UserInterFace;

public class Logic {
	private static final logicFacade logic = logicFacade.getInstance();
	private final static BackendFacade BACKEND_FACADE = BackendFacade.getBackendFacade();

	public static void editAddress(String userName, String street, String city, String zipCode) {
		// Edit user's address by using their user name to locate
		Printer.printLogicLoading();
		if (userName.length() >= 10) { // @userName should less than 10 character.
			Printer.printErrInput("User Name", "10");
		} else {
			if (street.length() <= 100 && city.length() <= 50 && zipCode.length() <= 10) { // the max is street 100
																							// character, city 50 and
																							// zip 10.
				GoogleGeocode geocode = logic.getLatLng(street, city, zipCode);
				String lat = geocode.getLat();
				String lng = geocode.getLng();
				Address address = new Address(street, city, zipCode, Double.parseDouble(lat), Double.parseDouble(lng));
				BACKEND_FACADE.editSenderAddress(userName, address);
				Printer.printLogicRequestSuccess("edit address");
			} else {
				Printer.printLogicErrAddress();
			}
		}
	}

	public static void register(String userName, String realName, String street, String city, String zipCode) {
		Printer.printLogicLoading();
		// TODO: Using @login to search is there a login exist
		boolean legal = true;
		double lat = 0;
		double lng = 0;
		String status = logic.checkUser(userName);
		if (userName.length() >= 10 || userName.isEmpty()) {// @userName should less than 10 character.
			Printer.printErrInput("User Name", "10");
			legal = false;

		} else if ("VIP".equals(status) || "Active".equals(status)) {
			Printer.printLogicUserFound(userName);
			legal = false;
		}
		if (realName.length() >= 100 || realName.isEmpty()) {// @realname should less than 100 character
			Printer.printErrInput("Name", "100");
			legal = false;
		}
		if (street.length() >= 100 || street.isEmpty()) {
			// the max is street 100
			// character, city 50 and
			// zip 10.
			Printer.printLogicErrAddress();
			legal = false;
		}
		if (city.length() >= 50 || city.isEmpty()) {
			Printer.printLogicErrAddress();
			legal = false;
		}
		if (zipCode.length() >= 10 || zipCode.isEmpty()) {
			Printer.printLogicErrAddress();
			legal = false;
		}
		if (legal) { // If meet the requirement check Geocode, and check address valid.
			GoogleGeocode geocode = logic.getLatLng(street, city, zipCode);
			lat = Double.parseDouble(geocode.getLat());
			lng = Double.parseDouble(geocode.getLng());
			if (lat == 0 && lng == 0) {
				Printer.printLogicAddressNotFound();
				legal = false;
			}
		}
		if (legal) {// If everything meet the requirement, sign up the account

			Printer.printLogicRequestSuccess("sign up an account, your user name is " + userName);
			Address address = new Address(street, city, zipCode, lat, lng);
			Sender sender = new Sender(address, realName, userName);
			BACKEND_FACADE.addUser(sender);
		}
	}

	public static boolean isSender(String trackingNumber) {
		Printer.printLogicLoading();
		boolean isSender = false;
		for (edu.unl.cse.csce361.package_tracker.backend.Package p : UserInterFace.getSender().getPackageSet()) {
			if (p.getTrackingNumber().equals(trackingNumber)) {
				isSender = true;
			}

		}
		return isSender;
	}

	public static boolean isNumber(String s) {
		// check if user is typing number.
		for (int i = 0; i < s.length(); i++)
			if (!Character.isDigit(s.charAt(i)))
				return false;
		return true;
	}

}
