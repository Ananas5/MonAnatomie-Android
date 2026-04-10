package com.example.monanatomie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed // Importé pour gérer les index
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.monanatomie.ui.theme.MonAnatomieTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonAnatomieTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigation(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(navController = navController)
                        }

                        composable("profile") {
                            ProfileScreen()
                        }

                        composable("detail/{index}") { backStackEntry ->
                            val indexString = backStackEntry.arguments?.getString("index")
                            val index = indexString?.toIntOrNull() ?: 0

                            // Sécurité : on vérifie que l'index existe dans la liste
                            if (index in DetailList.indices) {
                                DetailScreen(item = DetailList[index], onBack = { navController.popBackStack() })
                            } else {
                                Text("Erreur : Elément introuvable")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnatomyElement(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.clickable { onClick() } // Correction : le clickable doit être ici
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(255.dp)
        ) {
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

// Modifié pour passer l'index de départ pour la navigation
@Composable
fun ElementGrid(
    data: List<ElementData>,
    startIndex: Int, // On ajoute l'index de départ
    onItemClick: (Int) -> Unit, // On renvoie l'index cliqué
    modifier: Modifier = Modifier
){
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.height(168.dp)
    ) {
        itemsIndexed(data) { index, item ->
            AnatomyElement(
                item.drawable,
                item.text,
                onClick = { onItemClick(startIndex + index) }
            )
        }
    }
}

@Composable
fun ElementRow(
    data: List<ElementData>,
    startIndex: Int,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        itemsIndexed(data) { index, item ->
            AnatomyElement(
                item.drawable,
                item.text,
                onClick = { onItemClick(startIndex + index) }
            )
        }
    }
}

@Composable
fun Section(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier.verticalScroll(rememberScrollState())
    ) {
        Text(stringResource(R.string.title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Red,
            modifier = Modifier.padding(16.dp))

        // IMPORTANT : On calcule l'index basé sur l'ordre dans DetailList
        Section(R.string.titre_cerveau){
            ElementGrid(
                data = CerveauData,
                startIndex = 0,
                onItemClick = { index -> navController.navigate("detail/$index") }
            )
        }
        Section(R.string.titre_coeurpourmons){
            ElementGrid(
                data = CoeurPoumonsData,
                startIndex = 4, // Commence après les 4 éléments du cerveau
                onItemClick = { index -> navController.navigate("detail/$index") }
            )
        }
        Section(R.string.titre_structure){
            ElementGrid(
                data = OsMusclesPeauData,
                startIndex = 9, // Commence après cerveau(4) + coeur(5)
                onItemClick = { index -> navController.navigate("detail/$index") }
            )
        }
        Section(R.string.titre_digestion) {
            ElementRow(
                data = DigestionData,
                startIndex = 14, // Commence après les autres (14 éléments au total avant)
                onItemClick = { index -> navController.navigate("detail/$index") }
            )
        }
    }
}

@Composable
fun DetailScreen(item: DetailItem, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(item.title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(item.description),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onBack) {
            Text("Retour")
        }
    }
}

@Composable
fun AvatarElement(onAvatarSelected: (Int) -> Unit, modifier: Modifier = Modifier) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(AvatarList) { item ->
            Image(
                painter = painterResource(item),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .clickable { onAvatarSelected(item) }
            )
        }
    }
}

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val profileState = getProfile(context).collectAsState(initial = "Super Explorateur !" to R.drawable.avatar1)
    val name = profileState.value.first
    val avatar = profileState.value.second
    var isEditing by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(avatar),
            contentDescription = null,
            modifier = Modifier.size(120.dp).clip(CircleShape)
        )
        Text(text = name, style = MaterialTheme.typography.headlineMedium)
        Button(onClick = { isEditing = true }) {
            Text("Modifier")
        }

        if (isEditing) {
            var tempName by remember { mutableStateOf(name) }
            var tempAvatar by remember { mutableStateOf(avatar) }

            OutlinedTextField(
                value = tempName,
                onValueChange = { tempName = it },
                label = { Text("Nom") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            AvatarElement(onAvatarSelected = { tempAvatar = it })

            Button(onClick = {
                isEditing = false
                CoroutineScope(Dispatchers.IO).launch {
                    saveProfile(context, tempName, tempAvatar)
                }
            }) {
                Text("Valider")
            }
        }
    }
}

@Composable
private fun BottomNavigation(navController: NavController, modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text(stringResource(R.string.home)) },
            selected = currentRoute == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
            label = { Text(stringResource(R.string.profil)) },
            selected = currentRoute == "profile",
            onClick = {
                navController.navigate("profile") {
                    launchSingleTop = true
                }
            }
        )
    }
}