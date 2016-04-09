/*
 * XmppNamespaceDefine.java Creator: joe.zhao
 * Create-Date: 下午8:40:45
 */
package com.hikvision.push.androidpn.server.xmpp;

/**
 * 
 * 
 * @version $Id: XmppNamespaceDefine, v 0.1 2015年5月13日 下午8:40:45 Exp $
 */
public interface XMPPNamespaces {
	
	/** {@value} */
	public static final String	NOTIFICATION	= "androidpn:iq:notification";
	/** {@value} */
	public static final String	AUTH			= "jabber:iq:auth";
	
	/** {@value} */
	public static final String	CLIENT			= "jabber:client";
	/** {@value} */
	public static final String	SERVER			= "jabber:server";
	
	/** {@value} */
	public static final String	ACCEPT			= "jabber:component:accept";
	/** {@value} */
	public static final String	DATA			= "jabber:x:data";
	/** {@value} */
	public static final String	DELAY_LEGACY	= "jabber:x:delay";
	/** {@value} */
	public static final String	ROSTER			= "jabber:iq:roster";
	/** {@value} */
	public static final String	REGISTER		= "jabber:iq:register";
	/** {@value} */
	public static final String	SEARCH			= "jabber:iq:search";
	/** {@value} */
	public static final String	PRIVATE			= "jabber:iq:private";
	/** {@value} */
	public static final String	PRIVACY			= "jabber:iq:privacy";
	
	/** {@value} */
	public static final String	XBOSH			= "urn:xmpp:xbosh";
	/** {@value} */
	public static final String	DELAY			= "urn:xmpp:delay";
	/** {@value} */
	public static final String	SESSION			= "urn:ietf:params:xml:ns:xmpp-session";
	/** {@value} */
	public static final String	BIND			= "urn:ietf:params:xml:ns:xmpp-bind";
	/** {@value} */
	public static final String	SASL			= "urn:ietf:params:xml:ns:xmpp-sasl";
	
	/** {@value} */
	public static final String	STREAM			= "http://etherx.jabber.org/streams";
	
	/** {@value} */
	public static final String	DISCO_INFO		= "http://jabber.org/protocol/disco#info";
	/** {@value} */
	public static final String	DISCO_ITEMS		= "http://jabber.org/protocol/disco#items";
	
	/** {@value} */
	public static final String	MUC				= "http://jabber.org/protocol/muc";
	/** {@value} */
	public static final String	MUC_ADMIN		= "http://jabber.org/protocol/muc#admin";
	/** {@value} */
	public static final String	MUC_OWNER		= "http://jabber.org/protocol/muc#owner";
	/** {@value} */
	public static final String	MUC_USER		= "http://jabber.org/protocol/muc#user";
	/** {@value} */
	public static final String	MUC_UNIQUE		= "http://jabber.org/protocol/muc#unique";
	
	/** {@value} */
	public static final String	HTTPBIND		= "http://jabber.org/protocol/httpbind";
	/** {@value} */
	public static final String	CHATSTATES		= "http://jabber.org/protocol/chatstates";
	/** {@value} */
	public static final String	NICK			= "http://jabber.org/protocol/nick";
	
}
