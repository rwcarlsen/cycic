Overview
========
Cycic (pronounced 'psychic') is a fuel cycle input generator.  This tool is closely 
coupled to the data model of the discrete time nuclear fuel cycle code `cyclus`_.  
Currently, cycic is implemented in pure JavaScript and is accessible from any 
modern web browser.  This leverages a number of excellent libraries, such as `jQuery`_
and `d3`_ for building interfaces and graphs.

Cycic's design is broken up into two fundamental parts: the user-facing views and
the backend model.  Previous incarnations of cycic were called `bright.ui`_ and were
written in Python using - at various times - `VisTrails`_ and the 
`Enthought Tool Suite`_.

Models
------
Cyclus uses `RelaxNG`_ (RNG) to create and validate fuel cycle components
and the simulation as a whole.  Cyclus has a `multi-tiered modeling paradigm`_,
including facilities, markets, regions, and institutions.  Abstract schemas for 
each of these types of models are provided through associated RNG files to the 
underlying C++ code.  

The cycic tool takes these RNG schema files and converts them to a JSON object 
representing the same information.  These in-memory JSON objects are then used 
to generate facility, market, region, or institution specific forms for creating 
instances of these models.  Once a new instance is created by the user, it 
is stored in a simulation object.  These simulation objects may be persisted either 
as JSON objects themselves or converted to XML.  This XML representation is 
valid input for the cyclus itself.  See Figure 1 for a graphical display of 
this process.

.. figure:: _static/cycic_models.svg
    :align: center
    :width: 75%

    **Figure 1:** cycic and cyclus interaction and conversion workflow. 


Views
-----
The implementation of the views is separated from that of the models.  This 
follows an MVC pattern, where cyclus itself is the controller and external to 
cycic.  The basic views are forms for creating model instances based off of 
provided schemas.  The model types are the same as in cycic:

* facilities, 
* markets, 
* regions, 
* and institutions.

However, the value added by cycic is that the user may build up from model instances
a fuel cycle simulation.  This is done by connecting concrete facilities, markets,
etc to each other.  This connections may be made in a variety of ways, including 
a drag-and-drop interface.  To enable the GUI interactions, the fuel cycle itself
is displayed in a node-edge or network view.  Currently, this displays a 
market-centered rendering of the state of the fuel cycle. Additional representations
are possible, including region-based (map, lat-long, etc), institution-centric, 
or a traditional mass flow diagram (using only the facilities from the market view).
Further visualizations and interactions, such as the time line or the tables of
existing model instance, also help the user interrogate and manipulate the state of 
the simulation.


.. _cyclus: http://cyclus.github.com/

.. _jQuery: http://jquery.com/

.. _d3: http://d3js.org/

.. _bright.ui: http://bright-dev.github.com/

.. _VisTrails: http://www.vistrails.org/index.php/Main_Page

.. _Enthought Tool Suite: http://code.enthought.com/projects/index.php

.. _RelaxNG: http://relaxng.org/

.. _multi-tiered modeling paradigm: http://cyclus.github.com/basics/introduction.html#modeling-paradigm
