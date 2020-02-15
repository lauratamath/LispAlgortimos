;Universidad del Valle de Guatemala
;Algoritmos y Estructuras de datos
;SISTEMA EXPERTO
;
;Inspirado en Lisp Expert System de Joao Medeiros (jpzm)
;Extraido de: https://gist.github.com/jpzm/ff72beaedd4f381e6defffe701e7594f
;Julio Herrera 19402
;Oliver De Leon 19
;Laura Tamath 19

;Definiendo reglas
(defvar database)
(setq database '(
    (rule (and fiebre congestion) 'gripe)
    (rule (and deshidratacion) 'diarrea)
    (rule (and ardor) 'alergia)
    (rule (and dolorMuscular ardor) 'hematoma)
    (rule (and reflujo ardor) 'gastritis)
))

;Definiendo terminos
(defvar fiebre)
(defvar congestion)
(defvar deshidratacion)
(defvar ardor)
(defvar dolorMuscular)
(defvar reflujo)

;Llamando al motor de inferencia
;nth: Sirve para llamar al elemento indicado de una lista
;Ejemplo: (nth 2 i), llama al segundo elemento de la lista (iterador actual)
(defun play (knowledge state)
    (setq fiebre nil)
    (setq congestion nil)
    (setq deshidratacion nil)
    (setq ardor 'true)
    (setq dolorMuscular nil)
    (setq reflujo 'true)
    (loop for i in knowledge do
        (if (eval (nth 1 i))
            (print (nth 2 i))
        )
    )
)

;Ejecutando interface Hombre-Maquina
(play database '())
