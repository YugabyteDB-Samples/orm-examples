<?php

use App\Http\Controllers\OrderController;
use App\Http\Controllers\ProductController;
use App\Http\Controllers\UserController;
use App\Http\Resources\OrderResource;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Resources\UserResource;
use App\Models\User;
use App\Http\Resources\ProductResource;
use App\Models\Orders;
use App\Models\Products;
/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('/users', function () {
    return UserResource::collection(User::all());
});

Route::get('/products', function () {
    return ProductResource::collection(Products::all());
});

Route::get('/orders', function () {
    return OrderResource::collection(Orders::all());
});

Route::post('/products', [ProductController::class, 'store']);

Route::post('/users', [UserController::class, 'store']);

Route::post('/orders', [OrderController::class, 'store']);