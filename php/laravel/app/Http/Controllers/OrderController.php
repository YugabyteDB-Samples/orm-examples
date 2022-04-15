<?php

namespace App\Http\Controllers;

use App\Models\Orders;
use App\Models\OrderLines;
use App\Models\User;
use Illuminate\Http\Request;

class OrderController extends Controller
{
    public function index()
    {
        return Orders::all();
    }
 
    public function show($id)
    {
        return Orders::find($id);
    }

    public function store(Request $request)
    {
        // $order = Orders::create($request->all());

        $order = new Orders();
        $user = User::findOrFail($request->userId);
        // $order -> Orders()
        //     -> where('user_id', $user->user_id);
        // echo($this->$request);
        // echo($this->$user);
        
        $order -> user_id = $user->user_id;
        $savedOrder = Orders::create($order->getFillable());
        return response()->json( $savedOrder, 201);
    }

    public function update(Request $request, $id)
    {
        $article = Orders::findOrFail($id);
        $article->update($request->all());

        return $article;
    }

    public function delete(Request $request, $id)
    {
        $article = Orders::findOrFail($id);
        $article->delete();

        return 204;
    }
}
