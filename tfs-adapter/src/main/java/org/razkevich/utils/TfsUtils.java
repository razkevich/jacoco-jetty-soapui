package org.razkevich.utils;

import org.razkevich.business.model.TfsRuntimeException;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

public final class TfsUtils {

	private TfsUtils() {
	}

	public static String generateUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static XMLGregorianCalendar toXMLCalendar(Calendar calendar) {
		try {
			GregorianCalendar gCalendar = new GregorianCalendar();
			gCalendar.setTimeInMillis(calendar.getTimeInMillis());
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
		} catch (Exception e) {
			throw new TfsRuntimeException("Failed to create XMLGregorianCalendar", e);
		}
	}
}
