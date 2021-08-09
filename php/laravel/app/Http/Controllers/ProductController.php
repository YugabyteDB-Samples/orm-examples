<?php

namespace App\Http\Controllers;

use App\Models\Products;
use Illuminate\Http\Request;

class ProductController extends Controller
{

    public function index()
    {
        return Products::all();
    }
 
    public function show($id)
    {
        return Products::find($id);
    }

    public function store(Request $request)
    {
        $product = Products::create($request->all());
        return response()->json($product, 201);
    }

    public function update(Request $request, $id)
    {
        $article = Products::findOrFail($id);
        $article->update($request->all());

        return $article;
    }

    public function delete(Request $request, $id)
    {
        $article = Products::findOrFail($id);
        $article->delete();

        return 204;
    }
}
