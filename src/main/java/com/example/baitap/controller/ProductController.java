package com.example.baitap.controller;

import com.example.baitap.model.Product;
import com.example.baitap.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IProductService productService;

    @ModelAttribute("products")
    public Page<Product> products(@PageableDefault(value = 2) @SortDefault (sort = {"price"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return productService.findAll(pageable);
    }

    @ModelAttribute("product")
    public Product product() {
        return new Product();
    }

    @GetMapping
    public ModelAndView findAllProduct() {
        return new ModelAndView("display");
    }

    @GetMapping("/page")
    public ModelAndView findAllProvincePage(@RequestParam("search_page") Optional<String> name, @PageableDefault(value = 2) @SortDefault (sort = {"price"}, direction = Sort.Direction.ASC) Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("display");
        Page<Product> ProductPage;
        if (name.isPresent()){
            ProductPage = productService.findPageBySearch(name.get(), pageable);
            modelAndView.addObject("search_page", name.get());
        }else {
            ProductPage = productService.findPage(pageable);
        }
        modelAndView.addObject("products", ProductPage);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createProduct() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("product") Optional<Product> product,
                         RedirectAttributes redirectAttributes) {
        if (product.isPresent()) {
            MultipartFile multipartFile = product.get().getImage();
            String imageUrl = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(multipartFile.getBytes(),new File(fileUpload + multipartFile.getOriginalFilename()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            product.get().setImageUrl(imageUrl);
            productService.save(product.get());
        } else {
            redirectAttributes.addFlashAttribute("message", "Create fail!");
        }
        redirectAttributes.addFlashAttribute("message", "Create successfully!");
        return "redirect:/product";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateProvince(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("update");
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            modelAndView.addObject("product", product.get());
        } else {
            modelAndView.addObject("message", "Not exist province!");
        }
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("product") Optional<Product> product,
                         RedirectAttributes redirectAttributes) {
        if (product.isPresent()) {
            MultipartFile multipartFile = product.get().getImage();
            String imageUrl = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(multipartFile.getBytes(),new File(fileUpload + multipartFile.getOriginalFilename()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            product.get().setImageUrl(imageUrl);
            productService.save(product.get());
        } else {
            redirectAttributes.addFlashAttribute("messageEdit", "Update fail!");
        }
        redirectAttributes.addFlashAttribute("messageEdit", "Chú chó hư hỏng");
        return "redirect:http://localhost:8081/product/update/" + product.get().getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes) {
        productService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Delete successfully!");
        return "redirect:/product";
    }

    @PostMapping("/search")
    public ModelAndView search(@RequestParam("search") Optional<String> name, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("display");
        if (name.isPresent()) {
            Page<Product> products = productService.findBySearch(name.get(),pageable);
            modelAndView.addObject("products", products);
        }
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView findById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("detail");
        Optional<Product> product = productService.findById(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @GetMapping("/findAll")
    public ModelAndView findAllProDuctByPrice(@SortDefault(sort = {"price"}) Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("display");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/findMaxPrice")
    public String findMaxPrice(Pageable pageable, RedirectAttributes redirectAttributes) {
        Page<Product> products = productService.findMaxPrice(pageable);
        redirectAttributes.addFlashAttribute("products", products);
        return "redirect:http://localhost:8081/product";
    }

    @GetMapping("/findMinPrice")
    public String findMinPrice(Pageable pageable, RedirectAttributes redirectAttributes) {
        Page<Product> products = productService.findMinPrice(pageable);
        redirectAttributes.addFlashAttribute("products", products);
        return "redirect:http://localhost:8081/product";
    }

    @GetMapping("/findMaxQuantity")
    public String findMaxQuantity(Pageable pageable, RedirectAttributes redirectAttributes) {
        Page<Product> products = productService.findMaxQuantity(pageable);
        redirectAttributes.addFlashAttribute("products", products);
        return "redirect:http://localhost:8081/product";
    }

    @GetMapping("/findMinQuantity")
    public String findMinQuantity(Pageable pageable, RedirectAttributes redirectAttributes) {
        Page<Product> products = productService.findMinQuantity(pageable);
        redirectAttributes.addFlashAttribute("products", products);
        return "redirect:http://localhost:8081/product";
    }
}
