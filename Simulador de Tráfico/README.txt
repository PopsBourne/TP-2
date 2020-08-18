Cuando se crea un proyecto nuevo de la 5 y otro de la 6, cuando ya tengamos el src en dichos proyectos
debemos importar en ambos proyecto el .jar que se encuentra en el paquete codigo/lib.
Dando a Project->properties->Java Build Path->AddJARs...

Si desde Run configurations queremos abrir un .ini para la GUI debemos tener ese .ini a la altura de la carpeta de src
para visualizarlo correctamente cargado en nuestra práctica.

Si queremos ejecutar normalmente GUI en run configurations ponemos "-m gui".