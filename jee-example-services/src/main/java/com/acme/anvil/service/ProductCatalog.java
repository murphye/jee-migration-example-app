package com.acme.anvil.service;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface ProductCatalog extends EJBObject {
	public void populateCatalog() throws RemoteException;
}
