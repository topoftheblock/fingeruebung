

   
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


/** Informationen zur sprechenden Person.
 * Updated by JCasGen Mon May 04 12:30:41 CEST 2026
 * XML source: /Users/andreasblock/Desktop/6.Semester/Praktikum/fingeruebung/target/jcasgen/typesystem.xml
 * @generated */
public class Redner extends Annotation {
 
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static String _TypeName = "org.texttechnologylab.practical.sose26.christian.type.Redner";
  
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Redner.class);
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
   
  public final static String _FeatName_vorname = "vorname";
  public final static String _FeatName_nachname = "nachname";
  public final static String _FeatName_partei = "partei";
  public final static String _FeatName_funktion = "funktion";


  /* Feature Adjusted Offsets */
  private final static CallSite _FC_vorname = TypeSystemImpl.createCallSite(Redner.class, "vorname");
  private final static MethodHandle _FH_vorname = _FC_vorname.dynamicInvoker();
  private final static CallSite _FC_nachname = TypeSystemImpl.createCallSite(Redner.class, "nachname");
  private final static MethodHandle _FH_nachname = _FC_nachname.dynamicInvoker();
  private final static CallSite _FC_partei = TypeSystemImpl.createCallSite(Redner.class, "partei");
  private final static MethodHandle _FH_partei = _FC_partei.dynamicInvoker();
  private final static CallSite _FC_funktion = TypeSystemImpl.createCallSite(Redner.class, "funktion");
  private final static MethodHandle _FH_funktion = _FC_funktion.dynamicInvoker();

   
  /** Never called.  Disable default constructor
   * @generated */
  @Deprecated
  @SuppressWarnings ("deprecation")
  protected Redner() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param casImpl the CAS this Feature Structure belongs to
   * @param type the type of this Feature Structure 
   */
  public Redner(TypeImpl type, CASImpl casImpl) {
    super(type, casImpl);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Redner(JCas jcas) {
    super(jcas);
    readObject();   
  } 


  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Redner(JCas jcas, int begin, int end) {
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
  //* Feature: vorname

  /** getter for vorname - gets Der Vorname des Redners.
   * @generated
   * @return value of the feature 
   */
  public String getVorname() { 
    return _getStringValueNc(wrapGetIntCatchException(_FH_vorname));
  }
    
  /** setter for vorname - sets Der Vorname des Redners. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setVorname(String v) {
    _setStringValueNfc(wrapGetIntCatchException(_FH_vorname), v);
  }    
    
   
    
  //*--------------*
  //* Feature: nachname

  /** getter for nachname - gets Der Nachname des Redners.
   * @generated
   * @return value of the feature 
   */
  public String getNachname() { 
    return _getStringValueNc(wrapGetIntCatchException(_FH_nachname));
  }
    
  /** setter for nachname - sets Der Nachname des Redners. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setNachname(String v) {
    _setStringValueNfc(wrapGetIntCatchException(_FH_nachname), v);
  }    
    
   
    
  //*--------------*
  //* Feature: partei

  /** getter for partei - gets Die Fraktion oder Partei des Redners.
   * @generated
   * @return value of the feature 
   */
  public String getPartei() { 
    return _getStringValueNc(wrapGetIntCatchException(_FH_partei));
  }
    
  /** setter for partei - sets Die Fraktion oder Partei des Redners. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPartei(String v) {
    _setStringValueNfc(wrapGetIntCatchException(_FH_partei), v);
  }    
    
   
    
  //*--------------*
  //* Feature: funktion

  /** getter for funktion - gets Die Rolle des Redners.
   * @generated
   * @return value of the feature 
   */
  public String getFunktion() { 
    return _getStringValueNc(wrapGetIntCatchException(_FH_funktion));
  }
    
  /** setter for funktion - sets Die Rolle des Redners. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setFunktion(String v) {
    _setStringValueNfc(wrapGetIntCatchException(_FH_funktion), v);
  }    
    
  }

    