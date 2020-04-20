package com.owners.gravitas.dto;

import java.util.List;

public class LeadDetailsList extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -7530982882647320438L;

    /** The lead details list */
    private List< LeadDetails > leadDetailsList;
    
    /** The total number of pages */
    private long totalPages;

    /** The total elements */
    private long totalElements;

    /** The number of elements */
    private long numberOfElements;
    
    private long pageNumber;

    /**
     * @return the leadDetailsList
     */
    public List< LeadDetails > getLeadDetailsList() {
        return leadDetailsList;
    }

    /**
     * @param leadDetailsList
     *            the leadDetailsList to set
     */
    public void setLeadDetailsList( final List< LeadDetails > leadDetailsList ) {
        this.leadDetailsList = leadDetailsList;
    }

    /**
     * @return the totalPages
     */
    public long getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages
     *            the totalPages to set
     */
    public void setTotalPages( final long totalPages ) {
        this.totalPages = totalPages;
    }

    /**
     * @return the totalElements
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * @param totalElements
     *            the totalElements to set
     */
    public void setTotalElements( final long totalElements ) {
        this.totalElements = totalElements;
    }

    /**
     * @return the numberOfElements
     */
    public long getNumberOfElements() {
        return numberOfElements;
    }

    /**
     * @param numberOfElements
     *            the numberOfElements to set
     */
    public void setNumberOfElements( final long numberOfElements ) {
        this.numberOfElements = numberOfElements;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LeadDetailsList [leadDetailsList=" + leadDetailsList + ", totalPages=" + totalPages + ", totalElements="
                + totalElements + ", numberOfElements=" + numberOfElements + "]";
    }

    /**
     * @return the pageNumber
     */
    public long getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber( final long pageNumber ) {
        this.pageNumber = pageNumber;
    }

}
