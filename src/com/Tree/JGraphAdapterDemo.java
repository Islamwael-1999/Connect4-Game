package com.Tree;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import com.company.TreeNode;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * A demo applet that shows how to use JGraph to visualize JGraphT graphs.
 *
 * @author Barak Naveh
 *
 * @since Aug 3, 2003
 */
public class JGraphAdapterDemo<nodeID> extends JApplet {
    private static final Color     DEFAULT_BG_COLOR = Color.decode( "#FAFBFF" );
    private static final Dimension DEFAULT_SIZE = new Dimension( 530, 320 );

    //


    /**
     * @see java.applet.Applet#init().
     */
    // create a JGraphT graph
    ListenableGraph g = new ListenableDirectedGraph( DefaultEdge.class );

    // create a visualization using JGraph, via an adapter
    private JGraphModelAdapter m_jgAdapter=new JGraphModelAdapter( g );

    JGraph jgraph = new JGraph( m_jgAdapter );
    private static Integer nodeID=0;
    public void draw(TreeNode treeNode,boolean turn) {




        // add some sample data (graph manipulated via JGraphT)
//        g.addVertex( 22.0 );
//        g.addVertex( 23.0 );
//        g.addVertex( 24.0 );
//        g.addVertex( 28.0 );
//
//        g.addEdge( 22.0, 23.0 );
//        g.addEdge( 23.0, 24.0 );
//        g.addEdge( 24.0, 22.0 );
//        g.addEdge( 28.0, 24.0 );
//
//        // position vertices nicely within JGraph component
//        positionVertexAt( 22.0, 130, 40 );
//        positionVertexAt( 23.0, 60, 200 );
//        positionVertexAt( 24.0, 310, 230 );
//        positionVertexAt( 28.0, 380, 70 );
        Double parentHeurstic=treeNode.getHeurstic();
        String parent=parentHeurstic.toString()+"-"+nodeID.toString();
        g.addVertex(parent);
        DefaultGraphCell cell = m_jgAdapter.getVertexCell( parent );
        nodeID++;
        Map              attr = cell.getAttributes(  );
        Rectangle2D b    = GraphConstants.getBounds( attr );
        double x=b.getX();
        double y=b.getY();
        System.out.println(treeNode.getTreeNodes().size());
        y=y+60;
        for(TreeNode node:treeNode.getTreeNodes()) {
            Double childHeurstic=node.getHeurstic();
            String child=childHeurstic.toString()+"-"+nodeID.toString();
            g.addVertex( child);
            g.addEdge(parent,child);



            this.positionVertexAt(turn,child,(int)x,(int)y);
            x=x+120;
            nodeID++;
        }
        turn=!turn;
        for(TreeNode node:treeNode.getTreeNodes()) {
            draw(node,turn);
        }


    }


    private void adjustDisplaySettings( JGraph jg ) {
        jg.setPreferredSize( DEFAULT_SIZE );

        Color  c        = DEFAULT_BG_COLOR;
        String colorStr = null;

        try {
            colorStr = getParameter( "bgcolor" );
        }
        catch( Exception e ) {}

        if( colorStr != null ) {
            c = Color.decode( colorStr );
        }

        jg.setBackground( c );
    }


    private void positionVertexAt( boolean turn,Object vertex, int x, int y ) {
        DefaultGraphCell cell = m_jgAdapter.getVertexCell( vertex );
        Map              attr = cell.getAttributes(  );
        Rectangle2D b    = GraphConstants.getBounds( attr );
        Color colorAi= Color.RED;
        Color colorPerson = Color.YELLOW;
        if(turn)
            GraphConstants.setBackground(attr,colorAi);
        else
            GraphConstants.setBackground(attr,colorPerson);
        GraphConstants.setBounds( attr, new Rectangle( x, y, (int)b.getWidth(), (int)b.getHeight() ) );
        Map cellAttr = new HashMap(  );
        cellAttr.put( cell, attr );
        m_jgAdapter.edit(cellAttr,null,null,null);
    }
    public void showPane()
    {   adjustDisplaySettings( jgraph );
        getContentPane(  ).add( jgraph );
        resize( DEFAULT_SIZE );
        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(jgraph));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}