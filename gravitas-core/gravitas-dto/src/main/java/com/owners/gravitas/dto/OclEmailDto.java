package com.owners.gravitas.dto;

import static com.owners.gravitas.constants.Constants.REG_EXP_EMAIL;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.owners.gravitas.validators.ValidateLoanNumber;
import com.owners.gravitas.validators.ValidateLoanPhase;

/**
 * Holder for carrying data received from mortgage email.
 *
 * @author ankusht
 */
public class OclEmailDto {

	/** The email. */
	@NotBlank( message = "error.lead.email.required" )
	@Email( message = "error.lead.email.format", regexp = REG_EXP_EMAIL )
	@Size( min = 1, max = 80, message = "error.lead.email.size" )
	private String email;

	/** The first name. */
	@Size( min = 0, max = 40, message = "error.lead.firstName.size" )
	private String firstName;

	/** The last name. */
	@NotBlank( message = "error.lead.lastName.required" )
	@Size( min = 1, max = 80, message = "error.lead.lastName.size" )
	private String lastName;

	/** The loan phase. */
	@NotBlank( message = "error.ocl.email.loanphase.required" )
	@ValidateLoanPhase
	private String loanPhase;

	/** The trigger event. */
	private String triggerEvent;

	/** The assigned MLO. */
	private String assignedMLO;

	/** The status date. */
	private String statusDate;

	/** The note. */
	private String note;

	/** The loan number. */
	@NotBlank( message = "error.ocl.loannumber.required" )
	@ValidateLoanNumber
	private String loanNumber;

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail( final String email ) {
		this.email = email;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName( final String firstName ) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName( final String lastName ) {
		this.lastName = lastName;
	}

	/**
	 * Gets the loan phase.
	 *
	 * @return the loan phase
	 */
	public String getLoanPhase() {
		return loanPhase;
	}

	/**
	 * Sets the loan phase.
	 *
	 * @param loanPhase
	 *            the new loan phase
	 */
	public void setLoanPhase( final String loanPhase ) {
		this.loanPhase = loanPhase;
	}

	/**
	 * Gets the trigger event.
	 *
	 * @return the trigger event
	 */
	public String getTriggerEvent() {
		return triggerEvent;
	}

	/**
	 * Sets the trigger event.
	 *
	 * @param triggerEvent
	 *            the new trigger event
	 */
	public void setTriggerEvent( final String triggerEvent ) {
		this.triggerEvent = triggerEvent;
	}

	/**
	 * Gets the assigned MLO.
	 *
	 * @return the assigned MLO
	 */
	public String getAssignedMLO() {
		return assignedMLO;
	}

	/**
	 * Sets the assigned MLO.
	 *
	 * @param assignedMLO
	 *            the new assigned MLO
	 */
	public void setAssignedMLO( final String assignedMLO ) {
		this.assignedMLO = assignedMLO;
	}

	/**
	 * Gets the status date.
	 *
	 * @return the status date
	 */
	public String getStatusDate() {
		return statusDate;
	}

	/**
	 * Sets the status date.
	 *
	 * @param statusDate
	 *            the new status date
	 */
	public void setStatusDate( final String statusDate ) {
		this.statusDate = statusDate;
	}

	/**
	 * Gets the note.
	 *
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note.
	 *
	 * @param note
	 *            the new note
	 */
	public void setNote( final String note ) {
		this.note = note;
	}

	/**
	 * Gets the loan number.
	 *
	 * @return the loan number
	 */
	public String getLoanNumber() {
		return loanNumber;
	}

	/**
	 * Sets the loan number.
	 *
	 * @param loanNumber
	 *            the new loan number
	 */
	public void setLoanNumber( final String loanNumber ) {
		this.loanNumber = loanNumber;
	}

}
