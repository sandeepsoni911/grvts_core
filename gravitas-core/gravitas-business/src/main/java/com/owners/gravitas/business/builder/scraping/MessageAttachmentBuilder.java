package com.owners.gravitas.business.builder.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.exception.ApplicationException;

/**
 * To get attachment from message
 * @author sandeepsoni
 *
 */
@Component
public class MessageAttachmentBuilder extends AbstractBuilder< Message, List<Part> > {
	
	/** The Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger( MessageAttachmentBuilder.class);

	@Override
	public List<Part> convertTo(Message source, List<Part> destination) {
		List<Part> part = destination;
	        if (source != null) {
	            try {
	            		part = getMessageAttachmentList(source);
	            } catch ( Exception e ) {
	                throw new ApplicationException( "Problem in parsing the email ", e  );
	            }
	        }
	        return part;
	}

	/**
	 * To get attachments from message
	 * @return
	 * @throws IOException 
	 * @throws MessagingException 
	 */
	private List<Part> getMessageAttachmentList(Message source) throws MessagingException, IOException {
		List<Part> attachmentList = new ArrayList<Part>();
		Object content = source.getContent();
        if (content instanceof Multipart) {
        	attachmentList = handleMultipart((Multipart)content);
        } else {
        	Part part = handlePart(source);
        	attachmentList.add(part);
        }
		return attachmentList;
	}
	
	
	/**
	 * To go throw attachments
	 * @param multipart
	 * @throws MessagingException
	 * @throws IOException
	 */
	public List<Part> handleMultipart(Multipart multipart) throws MessagingException, IOException {
		List<Part> attachmentList = new ArrayList<Part>();
		for (int i = 0, n = multipart.getCount(); i < n; i++) {
			Part part = handlePart(multipart.getBodyPart(i));
			if(part != null) {
				attachmentList.add(part);
			}
		}
		return attachmentList;
	}

	/**
	 * This method reads particular attachment
	 * @param part
	 * @throws MessagingException
	 * @throws IOException
	 */
	public Part handlePart(Part part) throws MessagingException, IOException {
		Part attachment = null;
		if (part != null) {
			String disposition = part.getDisposition();
			String contentType = part.getContentType();
			if (disposition == null) {
				LOGGER.info("Null: " + contentType);
			} else if (disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
				LOGGER.info("attachment found with fileName and contentType respectively {} , {}"
						, part.getFileName(),
						contentType);
				attachment = part;
			}
		}
		return attachment;
	}

	@Override
	public Message convertFrom(List<Part> source, Message destination) {
		throw new UnsupportedOperationException("convertFrom operation is not supported");
	}

}
