/*
 * 下午1:33:12
 */
package com.xmpp.push.androidpn.server.xmpp.stanza;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import com.xmpp.push.androidpn.server.xmpp.xml.XMLBuilder;
import com.xmpp.push.androidpn.server.xmpp.xml.XMLElement;

/**
 * TODO
 * 
 * @version $Id: PacketError, v 0.1 2015年5月19日 下午1:33:12 Exp $
 */
public class PacketError {
	
	private static final String	ERROR_NAMESPACE	= "urn:ietf:params:xml:ns:xmpp-stanzas";
	protected final XMLElement	xml;
	
	public PacketError(final XMLElement xml) {
		this.xml = checkNotNull(xml);
	}
	
	public PacketError(Condition condition) {
		this(XMLBuilder.create("error").getXML());
	}
	
	public Type getType() {
		String type = xml.getAttribute("type");
		if (StringUtils.isNotEmpty(type)) {
			return Type.fromXMPP(type);
		}
		return null;
	}
	
	public void setType(Type type) {
		xml.setAttribute("type", null == type ? null : type.toXMPP());
	}
	
	public Condition getCondition() {
		for (Iterator<XMLElement> i = xml.getChildren().iterator(); i.hasNext();) {
			XMLElement el = i.next();
			if (el.getNamespace().equals(ERROR_NAMESPACE)
					&& !el.getTagName().equals("text")) {
				return Condition.fromXMPP(el.getTagName());
			}
		}
		
		String code = xml.getAttribute("code");
		if (StringUtils.isNotEmpty(code)) {
			return Condition.fromLegacyCode(Integer.parseInt(code));
		}
		return null;
	}
	
	public void setCondition(Condition condition) {
		xml.setAttribute("code", Integer.toString(condition.getLegacyCode()));
		XMLElement conditionElement = null;
		for (Iterator<XMLElement> i = xml.getChildren().iterator(); i.hasNext();) {
			XMLElement el = i.next();
			if (el.getNamespace().equals(ERROR_NAMESPACE)
					&& !el.getTagName().equals("text")) {
				conditionElement = el;
			}
		}
		
		if (null != conditionElement) {
			xml.removeChild(conditionElement);
		}
		conditionElement = XMLBuilder.create(condition.toXMPP(),
				ERROR_NAMESPACE).getXML();
		xml.addChild(conditionElement);
	}
	
	public enum Condition {
		
		/**
		 * The sender has sent XML that is malformed or that cannot be processed
		 * (e.g., an IQ stanza that includes an unrecognized value of the 'type'
		 * attribute); the associated error type SHOULD be "modify".
		 */
		bad_request("bad-request", Type.modify, 400),
		
		/**
		 * Access cannot be granted because an existing resource or session
		 * exists with the same name or address; the associated error type
		 * SHOULD be "cancel".
		 */
		conflict("conflict", Type.cancel, 409),
		
		/**
		 * The feature requested is not implemented by the recipient or server
		 * and therefore cannot be processed; the associated error type SHOULD
		 * be "cancel".
		 */
		feature_not_implemented("feature-not-implemented", Type.cancel, 501),
		
		/**
		 * The requesting entity does not possess the required permissions to
		 * perform the action; the associated error type SHOULD be "auth".
		 */
		forbidden("forbidden", Type.auth, 403),
		
		/**
		 * The recipient or server can no longer be contacted at this address
		 * (the error stanza MAY contain a new address in the XML character data
		 * of the <gone/> element); the associated error type SHOULD be
		 * "modify".
		 */
		gone("gone", Type.modify, 302),
		
		/**
		 * The server could not process the stanza because of a misconfiguration
		 * or an otherwise-undefined internal server error; the associated error
		 * type SHOULD be "wait".
		 */
		internal_server_error("internal-server-error", Type.wait, 500),
		
		/**
		 * The addressed JID or item requested cannot be found; the associated
		 * error type SHOULD be "cancel".
		 */
		item_not_found("item-not-found", Type.cancel, 404),
		
		/**
		 * The sending entity has provided or communicated an XMPP address
		 * (e.g., a value of the 'to' attribute) or aspect thereof (e.g., a
		 * resource identifier) that does not adhere to the syntax defined in
		 * Addressing Scheme (Section 3); the associated error type SHOULD be
		 * "modify".
		 */
		jid_malformed("jid-malformed", Type.modify, 400),
		
		/**
		 * The recipient or server understands the request but is refusing to
		 * process it because it does not meet criteria defined by the recipient
		 * or server (e.g., a local policy regarding acceptable words in
		 * messages); the associated error type SHOULD be "modify".
		 */
		not_acceptable("not-acceptable", Type.modify, 406),
		
		/**
		 * The recipient or server does not allow any entity to perform the
		 * action; the associated error type SHOULD be "cancel".
		 */
		not_allowed("not-allowed", Type.cancel, 405),
		
		/**
		 * The sender must provide proper credentials before being allowed to
		 * perform the action, or has provided improper credentials; the
		 * associated error type SHOULD be "auth".
		 */
		not_authorized("not-authorized", Type.auth, 401),
		
		/**
		 * The requesting entity is not authorized to access the requested
		 * service because payment is required; the associated error type SHOULD
		 * be "auth".
		 */
		payment_required("payment-required", Type.auth, 402),
		
		/**
		 * The intended recipient is temporarily unavailable; the associated
		 * error type SHOULD be "wait" (note: an application MUST NOT return
		 * this error if doing so would provide information about the intended
		 * recipient's network availability to an entity that is not authorized
		 * to know such information).
		 */
		recipient_unavailable("recipient-unavailable", Type.wait, 404),
		
		/**
		 * The recipient or server is redirecting requests for this information
		 * to another entity, usually temporarily (the error stanza SHOULD
		 * contain the alternate address, which MUST be a valid JID, in the XML
		 * character data of the &lt;redirect/&gt; element); the associated
		 * error type SHOULD be "modify".
		 */
		redirect("redirect", Type.modify, 302),
		
		/**
		 * The requesting entity is not authorized to access the requested
		 * service because registration is required; the associated error type
		 * SHOULD be "auth".
		 */
		registration_required("registration-required", Type.auth, 407),
		
		/**
		 * A remote server or service specified as part or all of the JID of the
		 * intended recipient does not exist; the associated error type SHOULD
		 * be "cancel".
		 */
		remote_server_not_found("remote-server-not-found", Type.cancel, 404),
		
		/**
		 * A remote server or service specified as part or all of the JID of the
		 * intended recipient (or required to fulfill a request) could not be
		 * contacted within a reasonable amount of time; the associated error
		 * type SHOULD be "wait".
		 */
		remote_server_timeout("remote-server-timeout", Type.wait, 504),
		
		/**
		 * The server or recipient lacks the system resources necessary to
		 * service the request; the associated error type SHOULD be "wait".
		 */
		resource_constraint("resource-constraint", Type.wait, 500),
		
		/**
		 * The server or recipient does not currently provide the requested
		 * service; the associated error type SHOULD be "cancel".
		 */
		service_unavailable("service-unavailable", Type.cancel, 503),
		
		/**
		 * The requesting entity is not authorized to access the requested
		 * service because a subscription is required; the associated error type
		 * SHOULD be "auth".
		 */
		subscription_required("subscription-required", Type.auth, 407),
		
		/**
		 * The error condition is not one of those defined by the other
		 * conditions in this list; any error type may be associated with this
		 * condition, and it SHOULD be used only in conjunction with an
		 * application-specific condition.
		 * <p>
		 *
		 * Implementation note: the default type for this condition is
		 * {@link Type#wait}, which is not specified in the XMPP protocol.
		 */
		undefined_condition("undefined-condition", Type.wait, 500),
		
		/**
		 * The recipient or server understood the request but was not expecting
		 * it at this time (e.g., the request was out of order); the associated
		 * error type SHOULD be "wait".
		 */
		unexpected_request("unexpected-request", Type.wait, 400);
		
		/**
		 * Converts a String value into its Condition representation.
		 *
		 * @param condition the String value.
		 * @return the condition corresponding to the String.
		 */
		public static Condition fromXMPP(String condition) {
			if (condition == null) {
				throw new NullPointerException();
			}
			condition = condition.toLowerCase();
			if (bad_request.toXMPP().equals(condition)) {
				return bad_request;
			} else if (conflict.toXMPP().equals(condition)) {
				return conflict;
			} else if (feature_not_implemented.toXMPP().equals(condition)) {
				return feature_not_implemented;
			} else if (forbidden.toXMPP().equals(condition)) {
				return forbidden;
			} else if (gone.toXMPP().equals(condition)) {
				return gone;
			} else if (internal_server_error.toXMPP().equals(condition)) {
				return internal_server_error;
			} else if (item_not_found.toXMPP().equals(condition)) {
				return item_not_found;
			} else if (jid_malformed.toXMPP().equals(condition)) {
				return jid_malformed;
			} else if (not_acceptable.toXMPP().equals(condition)) {
				return not_acceptable;
			} else if (not_allowed.toXMPP().equals(condition)) {
				return not_allowed;
			} else if (not_authorized.toXMPP().equals(condition)) {
				return not_authorized;
			} else if (payment_required.toXMPP().equals(condition)) {
				return payment_required;
			} else if (recipient_unavailable.toXMPP().equals(condition)) {
				return recipient_unavailable;
			} else if (redirect.toXMPP().equals(condition)) {
				return redirect;
			} else if (registration_required.toXMPP().equals(condition)) {
				return registration_required;
			} else if (remote_server_not_found.toXMPP().equals(condition)) {
				return remote_server_not_found;
			} else if (remote_server_timeout.toXMPP().equals(condition)) {
				return remote_server_timeout;
			} else if (resource_constraint.toXMPP().equals(condition)) {
				return resource_constraint;
			} else if (service_unavailable.toXMPP().equals(condition)) {
				return service_unavailable;
			} else if (subscription_required.toXMPP().equals(condition)) {
				return subscription_required;
			} else if (undefined_condition.toXMPP().equals(condition)) {
				return undefined_condition;
			} else if (unexpected_request.toXMPP().equals(condition)) {
				return unexpected_request;
			} else {
				throw new IllegalArgumentException("Condition invalid:"
						+ condition);
			}
		}
		
		public static Condition fromLegacyCode(int code) {
			if (bad_request.getLegacyCode() == code) {
				return bad_request;
			} else if (conflict.getLegacyCode() == code) {
				return conflict;
			} else if (feature_not_implemented.getLegacyCode() == code) {
				return feature_not_implemented;
			} else if (forbidden.getLegacyCode() == code) {
				return forbidden;
			} else if (gone.getLegacyCode() == code) {
				return gone;
			} else if (internal_server_error.getLegacyCode() == code) {
				return internal_server_error;
			} else if (item_not_found.getLegacyCode() == code) {
				return item_not_found;
			} else if (jid_malformed.getLegacyCode() == code) {
				return jid_malformed;
			} else if (not_acceptable.getLegacyCode() == code) {
				return not_acceptable;
			} else if (not_allowed.getLegacyCode() == code) {
				return not_allowed;
			} else if (not_authorized.getLegacyCode() == code) {
				return not_authorized;
			} else if (payment_required.getLegacyCode() == code) {
				return payment_required;
			} else if (recipient_unavailable.getLegacyCode() == code) {
				return recipient_unavailable;
			} else if (redirect.getLegacyCode() == code) {
				return redirect;
			} else if (registration_required.getLegacyCode() == code) {
				return registration_required;
			} else if (remote_server_not_found.getLegacyCode() == code) {
				return remote_server_not_found;
			} else if (remote_server_timeout.getLegacyCode() == code) {
				return remote_server_timeout;
			} else if (resource_constraint.getLegacyCode() == code) {
				return resource_constraint;
			} else if (service_unavailable.getLegacyCode() == code) {
				return service_unavailable;
			} else if (subscription_required.getLegacyCode() == code) {
				return subscription_required;
			} else if (undefined_condition.getLegacyCode() == code) {
				return undefined_condition;
			} else if (unexpected_request.getLegacyCode() == code) {
				return unexpected_request;
			} else {
				throw new IllegalArgumentException("Code invalid:" + code);
			}
		}
		
		private String	value;
		private int		code;
		private Type	defaultType;
		
		private Condition(String value, Type defaultType, int code) {
			this.value = value;
			this.defaultType = defaultType;
			this.code = code;
		}
		
		/**
		 * Returns the default {@link Type} associated with this condition. Each
		 * error condition has an error type that it is usually associated with.
		 *
		 * @return the default error type.
		 */
		public Type getDefaultType() {
			return defaultType;
		}
		
		/**
		 * Returns the legacy error code associated with the error. Error code
		 * mappings are based on XEP-0086 'Error Condition Mappings'. Support
		 * for legacy error codes is necessary since many "Jabber" clients do
		 * not understand XMPP error codes. The {@link #fromLegacyCode(int)}
		 * method will convert numeric error codes into Conditions.
		 *
		 * @return the legacy error code.
		 * @see <a href="http://xmpp.org/extensions/xep-0086.html">XEP-0086:
		 *      Error Condition Mappings</a>
		 */
		public int getLegacyCode() {
			return code;
		}
		
		/**
		 * Returns the error code as a valid XMPP error code string.
		 *
		 * @return the XMPP error code value.
		 */
		public String toXMPP() {
			return value;
		}
		
	}
	
	public enum Type {
		
		/**
		 * Do not retry (the error is unrecoverable).
		 */
		cancel("cancel"),
		
		/**
		 * Proceed (the condition was only a warning). This represents the
		 * "continue" error code in XMPP; because "continue" is a reserved
		 * keyword in Java the enum name has been changed.
		 */
		continue_processing("continue"),
		
		/**
		 * Retry after changing the data sent.
		 */
		modify("modify"),
		
		/**
		 * Retry after providing credentials.
		 */
		auth("auth"),
		
		/**
		 * Retry after waiting (the error is temporary).
		 */
		wait("wait");
		
		/**
		 * Converts a String value into its Type representation.
		 *
		 * @param type the String value.
		 * @return the condition corresponding to the String.
		 */
		public static Type fromXMPP(String type) {
			if (type == null) {
				throw new NullPointerException();
			}
			type = type.toLowerCase();
			if (cancel.toXMPP().equals(type)) {
				return cancel;
			} else if (continue_processing.toXMPP().equals(type)) {
				return continue_processing;
			} else if (modify.toXMPP().equals(type)) {
				return modify;
			} else if (auth.toXMPP().equals(type)) {
				return auth;
			} else if (wait.toXMPP().equals(type)) {
				return wait;
			} else {
				throw new IllegalArgumentException("Type invalid:" + type);
			}
		}
		
		private String	value;
		
		private Type(String value) {
			this.value = value;
		}
		
		/**
		 * Returns the error code as a valid XMPP error code string.
		 *
		 * @return the XMPP error code value.
		 */
		public String toXMPP() {
			return value;
		}
	}
	
}
