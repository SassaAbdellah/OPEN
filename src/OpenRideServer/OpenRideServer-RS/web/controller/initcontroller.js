/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/// Create the Namespace Manager that we'll use to
/// make creating namespaces a little easier.

if (typeof Namespace == 'undefined') var Namespace = {};
if (!Namespace.Manager) Namespace.Manager = {};

Namespace.Manager = {
 Register:function(namespace){
  namespace = namespace.split('.');

  if(!window[namespace[0]]) window[namespace[0]] = {};

  var strFullNamespace = namespace[0];
  for(var i = 1; i < namespace.length; i++)
  {
   strFullNamespace += "." + namespace[i];
   eval("if(!window." + strFullNamespace + ")window." + strFullNamespace + "={};");
  }
 }
};

//
Namespace.Manager.Register("fokus.openride.mobclient.controller");
Namespace.Manager.Register("fokus.openride.mobclient.controller.modules");

