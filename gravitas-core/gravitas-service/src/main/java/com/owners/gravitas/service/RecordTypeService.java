package com.owners.gravitas.service;

/**
 * The Interface RecordTypeService.
 *
 * @author ankusht
 * 
 *         The Interface RecordTypeService.
 */
public interface RecordTypeService {

	/**
	 * Gets the record type id by name.
	 *
	 * @param recordTypeName
	 *            the record type name
	 * @param objectType
	 *            the object type
	 * @return the record type id by name
	 */
	String getRecordTypeIdByName( String recordTypeName, String objectType );
}
