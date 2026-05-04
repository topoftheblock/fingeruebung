

   
/* Apache UIMA v3 - First created by JCasGen Mon May 04 12:30:41 CEST 2026 */

package org.texttechnologylab.practical.sose26.christian.type;
 

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;

import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.impl.TypeSystemImpl;
import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;


import org.apache.uima.jcas.tcas.Annotation;


/** Markiert den eigentlichen Redeinhalt.
 * Updated by JCasGen Mon May 04 12:30:41 CEST 2026
 * XML source: /Users/andreasblock/Desktop/6.Semester/Praktikum/fingeruebung/target/jcasgen/typesystem.xml
 * @generated */
public class Redeinhalt extends Annotation {
 
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static String _TypeName = "org.texttechnologylab.practical.sose26.christian.type.Redeinhalt";
  
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Redeinhalt.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
 
  /* *******************
   *   Feature Offsets *
   * *******************/ 
   
  public final static String _FeatName_redner = "redner";
  public final static String _FeatName_tagesordnungspunkt = "tagesordnungspunkt";
  public final static String _FeatName_redeTyp = "redeTyp";


  /* Feature Adjusted Offsets */
  private final static CallSite _FC_redner = TypeSystemImpl.createCallSite(Redeinhalt.class, "redner");
  private final static MethodHandle _FH_redner = _FC_redner.dynamicInvoker();
  private final static CallSite _FC_tagesordnungspunkt = TypeSystemImpl.createCallSite(Redeinhalt.class, "tagesordnungspunkt");
  private final static MethodHandle _FH_tagesordnungspunkt = _FC_tagesordnungspunkt.dynamicInvoker();
  private final static CallSite _FC_redeTyp = TypeSystemImpl.createCallSite(Redeinhalt.class, "redeTyp");
  private final static MethodHandle _FH_redeTyp = _FC_redeTyp.dynamicInvoker();

   
  /** Never called.  Disable default constructor
   * @generated */
  @Deprecated
  @SuppressWarnings ("deprecation")
  protected Redeinhalt() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param casImpl the CAS this Feature Structure belongs to
   * @param type the type of this Feature Structure 
   */
  public Redeinhalt(TypeImpl type, CASImpl casImpl) {
    super(type, casImpl);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Redeinhalt(JCas jcas) {
    super(jcas);
    readObject();   
  } 


  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Redeinhalt(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: redner

  /** getter for redner - gets Referenz auf die Person, die diese Rede hält.
   * @generated
   * @return value of the feature 
   */
  public Redner getRedner() { 
    return (Redner)(_getFeatureValueNc(wrapGetIntCatchException(_FH_redner)));
  }
    
  /** setter for redner - sets Referenz auf die Person, die diese Rede hält. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRedner(Redner v) {
    _setFeatureValueNcWj(wrapGetIntCatchException(_FH_redner), v);
  }    
    
   
    
  //*--------------*
  //* Feature: tagesordnungspunkt

  /** getter for tagesordnungspunkt - gets Der zugehörige Tagesordnungspunkt (TOP).
   * @generated
   * @return value of the feature 
   */
  public String getTagesordnungspunkt() { 
    return _getStringValueNc(wrapGetIntCatchException(_FH_tagesordnungspunkt));
  }
    
  /** setter for tagesordnungspunkt - sets Der zugehörige Tagesordnungspunkt (TOP). 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTagesordnungspunkt(String v) {
    _setStringValueNfc(wrapGetIntCatchException(_FH_tagesordnungspunkt), v);
  }    
    
   
    
  //*--------------*
  //* Feature: redeTyp

  /** getter for redeTyp - gets Reguläre Rede, Zwischenruf, etc.
   * @generated
   * @return value of the feature 
   */
  public String getRedeTyp() { 
    return _getStringValueNc(wrapGetIntCatchException(_FH_redeTyp));
  }
    
  /** setter for redeTyp - sets Reguläre Rede, Zwischenruf, etc. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRedeTyp(String v) {
    _setStringValueNfc(wrapGetIntCatchException(_FH_redeTyp), v);
  }    
    
  }

    