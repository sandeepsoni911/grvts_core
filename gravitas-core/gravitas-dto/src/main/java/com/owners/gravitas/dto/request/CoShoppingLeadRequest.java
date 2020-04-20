package com.owners.gravitas.dto.request;

import com.zuner.coshopping.model.lead.LeadModel;

public class CoShoppingLeadRequest extends BaseRequest {

	/*** The serialVersionUID */
	private static final long serialVersionUID = 5282439087985941618L;
	
	private LeadModel leadModel;

	/**
	 * @return the leadRequest
	 */
	public LeadModel getLeadModel() {
		return leadModel;
	}

	/**
	 * @param leadRequest the leadRequest to set
	 */
	public void setLeadModel(LeadModel leadModel) {
		this.leadModel = leadModel;
	}

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CoShoppingLeadRequest [leadModel=" + leadModel + "]";
    }
}
