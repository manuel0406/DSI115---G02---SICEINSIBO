package com.dsi.insibo.sice.Seguridad;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsi.insibo.sice.entity.Usuario;

@Controller
public class gestionarCredencialesController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/gestionarCredenciales")
    public String cargarUsuarioCredenciales(Model model, @RequestParam(required = false, defaultValue = "1") int pagina) {
        
        pagina=(pagina-1);
        List<Usuario> listadoUsuarios = usuarioService.listaUsuariosActivosIntervalos(pagina);

        List<UsuarioConNombre> listadoCompleto =new ArrayList<>();
        //Obtenemos los nombres
        for (Usuario usuario : listadoUsuarios) {
            String rol = usuario.getRolUsuario();
            String nombre="";

            if(rol.equals("Administrador")){
                nombre = "Administrador";
            }
            if(rol.equals("Docente")){

                nombre = usuario.getDocente().getNombreDocente() + " " + usuario.getDocente().getApellidoDocente() ;
            }
            if(rol.equals("Personal Administrativo")){
                nombre = usuario.getPersonalAdministrativo().getNombrePersonal()+ " " + usuario.getDocente().getApellidoDocente();
            }

            listadoCompleto.add(new UsuarioConNombre(usuario, nombre));
        }

        model.addAttribute("Usuarios", listadoCompleto);

        //Obtenemos la cantidad de usuarios que tenemos
        List<Usuario> totalUsuario = usuarioService.listaUsuariosActivos();
        int cantidad = (int) Math.ceil((double) totalUsuario.size() / 7);
        model.addAttribute("Cantidad", cantidad);


        return "/seguridad/gestionarCredenciales";
    }
}
