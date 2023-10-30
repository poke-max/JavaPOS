package org.fcyt;

import org.fcyt.controller.CajaController;
import org.fcyt.model.*;
import org.fcyt.view.GUIEntity;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) {
        EntityModel dao = new EntityModel("caja");
        GUIEntity caja = new GUIEntity(new JFrame(), true);
        CajaController ctrl = new CajaController(dao, caja);
    }
}
