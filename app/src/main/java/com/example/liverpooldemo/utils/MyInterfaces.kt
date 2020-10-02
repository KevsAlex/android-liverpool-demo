package com.example.liverpooldemo.utils

import android.view.View

/**
 *   Si version mejorada de DidSelectItemDelegate
 *   @view - el view sirve para animar la vista en transiciones
 *   @code - cualquier evento que quieras
 *   @position - la posicion del evento seleccionado
 *   @anyobject - cualquier objeto, para que sea recibido en su host
 */
interface DidPerformEvent {
    fun didPerformClick(view: View?, code: Int, position: Int, anyObject : Any)
}

/**
* Pensado para los dialogos
* code : Cualquier entero que quieras para identificar una accion
* EJ. Le puedes pasar 0 para un boton cancelar
*     y Un 1 para un boton confirmar
*
* response : En caso de que se requiera mandar un parámetro extra desde el dialogo
* ..... debería ser Any? el tipo de dato pero bueno ya lo implementé y me da flojera cambiarlo
*/
interface DidClickCustomDialog{
    fun didSelectDialogButton (code: Int , response : Any?)
}
