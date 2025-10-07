/* eslint-disable */
const functions = require("firebase-functions");
const admin = require("firebase-admin");
const express = require("express");
const cors = require("cors");

admin.initializeApp();
const db = admin.firestore();

const app = express();
app.use(cors({ origin: true }));
app.use(express.json());

// GET /foods/:barcode
app.get("/foods/:barcode", async (req, res) => {
  const { barcode } = req.params;
  const food = {
    barcode,
    name: "Oats 100g",
    calories: 389,
    protein: 16.9,
    carbs: 66.3,
    fat: 6.9,
  };
  res.json(food);
});

// POST /users/:uid/meals
app.post("/users/:uid/meals", async (req, res) => {
  try {
    const { uid } = req.params;
    const meal = {
      ...req.body,
      createdAt: admin.firestore.FieldValue.serverTimestamp(),
    };
    const ref = await db.collection("users").doc(uid).collection("meals").add(meal);
    res.status(201).json({ id: ref.id });
  } catch (e) {
    res.status(500).json({ error: String(e) });
  }
});

exports.api = functions.region("us-central1").https.onRequest(app);
