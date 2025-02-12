package com.crud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crud.model.Producto;
import com.crud.repository.ProductoRepository;

@Controller
@RequestMapping("/productos") // http://localhost:8080/productos
public class ProductoController {

	private final Logger logg = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private ProductoRepository productoRepository;

	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("productos", productoRepository.findAll());
		return "home"; 
	}

	@GetMapping("/create") // http://localhost:8080/productos/create
	public String create() {
		return "create";
	}

	@PostMapping("/save")
	public String save(Producto producto) {
		logg.info("Informacion del objeto producto: {}", producto);
		productoRepository.save(producto);
		return "redirect:/productos"; // Redirigir a la lista de productos
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		logg.info("Id del producto a editar: {}", id);
		Producto producto = productoRepository.findById(id).orElse(null);
		if (producto != null) {
			model.addAttribute("producto", producto);
			return "edit"; 
		}
		return "redirect:/productos"; // Si no encuentra el producto, redirige a la lista
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		logg.info("Id del producto a eliminar: {}", id);
		productoRepository.deleteById(id); // Elimina el producto por su ID
		return "redirect:/productos"; // Redirigir a la lista de productos
	}
}

