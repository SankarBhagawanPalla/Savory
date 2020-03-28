package com.springworks.savory.services;

import com.springworks.savory.commands.UnitOfMeasureCommand;
import com.springworks.savory.domain.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listofAllUoms();
}
